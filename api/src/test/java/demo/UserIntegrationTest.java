package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.SaveUserDto;
import demo.model.Sector;
import demo.model.User;
import demo.service.filter.DataTypes;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static demo.service.filter.DateCriteria.AFTER;
import static demo.service.filter.StringCriteria.CONTAINS;
import static demo.service.filter.StringCriteria.DOES_NOT_CONTAIN;
import static demo.service.filter.UserFieldNames.DOB;
import static demo.service.filter.UserFieldNames.NAME;
import static demo.service.validation.user_validator.UserErrors.NAME_DOESNT_CONTAIN_Q;
import static demo.service.validation.user_validator.UserErrors.NAME_NOT_UNIQUE;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIntegrationTest extends ContextIntegrationTest {

  @Test
  void findAll_shouldReturnPaginatedUsers() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
    createUser("user_a_q", true, List.of(sector_a));
    createUser("user_b_q", true, List.of(sector_a, sector_b));

    mockMvc.perform(get("/users")
                    .param("page", "0")
                    .param("sort", "name,asc")
                    .param("size", "1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.content.length()").value(1))
            .andExpect(jsonPath("$.content[0].name").value("user_a_q"))
            .andExpect(jsonPath("$.content[0].agreeToTerms").value(true))
            .andExpect(jsonPath("$.content[0].sectors.length()").value(1))
            .andExpect(jsonPath("$.content[0].sectors[0]").value("sector_a"))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
            .andExpect(jsonPath("$.pageable.pageSize").value(1));

    mockMvc.perform(get("/users")
                    .param("page", "0")
                    .param("sort", "name,desc")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2))
            .andExpect(jsonPath("$.content[1].name").value("user_a_q"))
            .andExpect(jsonPath("$.content[1].agreeToTerms").value(true))
            .andExpect(jsonPath("$.content[1].sectors.length()").value(1))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
            .andExpect(jsonPath("$.pageable.pageSize").value(10));
  }

  @Test
  void save_shouldSaveANewUser() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    createSector("sector_b", sector_a.getId(), 2);

    SaveUserDto dto = new SaveUserDto()
            .setSectorIds(List.of(1L, 2L))
            .setName("newquser")
            .setEmail("tere@gmail.com")
            .setPhoneNumber("+372 1234567")
            .setAgreeToTerms(true);
    byte[] bytes = getBytes(dto);
    mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("newquser"))
            .andExpect(jsonPath("$.agreeToTerms").value(true))
            .andExpect(jsonPath("$.phoneNumber").value("+372 1234567"))
            .andExpect(jsonPath("$.email").value("tere@gmail.com"))
            .andExpect(jsonPath("$.sectors.length()").value(2));
  }

  @Test
  void save_shouldThrowError_ifNameIncorrect() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    createSector("sector_b", sector_a.getId(), 2);

    SaveUserDto dto = new SaveUserDto()
            .setSectorIds(List.of(1L, 2L))
            .setName("Aasdasdas")
            .setAgreeToTerms(true);

    byte[] bytes = getBytes(dto);
    mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").value(NAME_DOESNT_CONTAIN_Q.getKood()));
  }

  @Test
  void save_shouldThrowError_ifNameExists() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
    createUser("user_a_q", true, List.of(sector_a, sector_b));

    SaveUserDto dto = new SaveUserDto()
            .setSectorIds(List.of(sector_a.getId(), sector_b.getId()))
            .setName("user_a_q")
            .setAgreeToTerms(true);

    byte[] bytes = getBytes(dto);
    mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").value(NAME_NOT_UNIQUE.getKood()));
  }

  @Test
  void update_shouldUpdateExistingUser() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
    User createdUser = createUser("user_a_q", true, List.of(sector_b, sector_a));

    SaveUserDto dto = new SaveUserDto()
            .setSectorIds(List.of(1L))
            .setName("newquser 2")
            .setAgreeToTerms(true);
    byte[] bytes = getBytes(dto);

    mockMvc.perform(put(String.format("/users/%s", createdUser.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("newquser 2"))
            .andExpect(jsonPath("$.agreeToTerms").value(true))
            .andExpect(jsonPath("$.id").value(createdUser.getId().toString()))
            .andExpect(jsonPath("$.sectors.length()").value(1));
  }

  @Test
  void update_shouldThrowError_ifUserDoesNotExist() throws Exception {
    SaveUserDto dto = new SaveUserDto()
            .setSectorIds(List.of(3L))
            .setName("new@user 2")
            .setAgreeToTerms(true);
    byte[] bytes = getBytes(dto);

    mockMvc.perform(put(String.format("/users/%s", UUID.randomUUID()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isNotFound());
  }

  @Test
  void delete_shouldDeleteUser_byId() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    User existingUser = createUser("user_a_q", true, List.of(sector_a));

    mockMvc.perform(delete(String.format("/users/%s", existingUser.getId())))
            .andExpect(status().isOk())
    ;
    List<User> existingUsers = findAll(User.class);
    assertEquals(0, existingUsers.size());
  }

  @Test
  void delete_shouldThrowError_ifUserDoesNotExist() throws Exception {
    mockMvc.perform(delete(String.format("/users/%s", UUID.randomUUID())))
            .andExpect(status().isNotFound())
            .andDo(print());
  }

  @Test
  void findAllBySector_shouldReturnUsersBySector() throws Exception {
    var users = createDefaultUsers();

    mockMvc.perform(get("/users/sector/1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
            .andExpect(jsonPath("$[0].sectors.length()").value(users.get(0).getSectors().size()))
            .andExpect(jsonPath("$[0].sectors[0]").value("Manufacturing"))
            .andExpect(jsonPath("$[1].name").value(users.get(3).getName()))
            .andExpect(jsonPath("$[1].sectors.length()").value(users.get(3).getSectors().size()))
            .andExpect(jsonPath("$[1].sectors[*]").value(containsInAnyOrder("Manufacturing", "Project furniture")))
    ;
  }

  @Test
  void findAllBySector_shouldReturnNotFound_ifSectorIdMissing() throws Exception {
    mockMvc.perform(get("/users/sector/99"))
            .andDo(print())
            .andExpect(status().isNotFound());
  }

  @Test
  void findAllByFilterId_STRING_CONTAINS_shouldReturnUsers() throws Exception {
    var users = createDefaultUsers();
    var mainUser = users.get(0);

    var filters = List.of(new FilterDto().setCriteria(CONTAINS.getKood()).setValue("Does").setType(DataTypes.STRING).setFieldName(NAME));
    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    mockMvc.perform(get(String.format("/users/filter/%s", userFilter.getId())))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("qJohn Does", "qJane Does", "qJack Doesn't")))
    ;

    filters = List.of(new FilterDto().setCriteria(CONTAINS.getKood()).setValue("abc").setType(DataTypes.STRING).setFieldName(NAME));
    userFilter = createUserFilter(filters, "user filter 1", mainUser);

    mockMvc.perform(get(String.format("/users/filter/%s", userFilter.getId())))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.length()").value(0))
    ;
  }

  @Test
  void findAllByFilterId_STRING_DOES_NOT_CONTAIN_shouldReturnUsers() throws Exception {
    var users = createDefaultUsers();
    var mainUser = users.get(0);

    var filters = List.of(new FilterDto().setCriteria(DOES_NOT_CONTAIN.getKood()).setValue("Does").setType(DataTypes.STRING).setFieldName(NAME));
    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    mockMvc.perform(get(String.format("/users/filter/%s", userFilter.getId())))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("[0].name").value("qJames Memorial"))
    ;
  }

  @Test
  void findAllByFilterId_STRING_COMPOSITE_shouldReturnUsers() throws Exception {
    var users = createDefaultUsers();
    var mainUser = users.get(0);

    var filter_1 = new FilterDto().setCriteria(DOES_NOT_CONTAIN.getKood()).setValue("qJane").setType(DataTypes.STRING).setFieldName(NAME);
    var filter_2 = new FilterDto().setCriteria(CONTAINS.getKood()).setValue("Does").setType(DataTypes.STRING).setFieldName(NAME);
    var filters = List.of(filter_2, filter_1);
    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    mockMvc.perform(get(String.format("/users/filter/%s", userFilter.getId())))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("qJohn Does", "qJack Doesn't")))
    ;
  }

  // Date
  @Test
  void findAllByFilterId_DATE_shouldReturnUsers() throws Exception {
    var users = createDefaultUsers();
    var mainUser = users.get(0);
// Instant.parse("1995-04-10T21:00:25.451157400Z")
    var filters = List.of(new FilterDto().setCriteria(AFTER.getKood()).setValue("2001-05-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB));
    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    mockMvc.perform(get(String.format("/users/filter/%s", userFilter.getId())))
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }
}