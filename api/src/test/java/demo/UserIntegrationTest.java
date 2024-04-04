package demo;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static demo.service.validation.user_validator.UserErrors.NAME_DOESNT_CONTAIN_Q;
import static demo.service.validation.user_validator.UserErrors.NAME_NOT_UNIQUE;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class UserIntegrationTest extends ContextIntegrationTest {

  @Test
  void findAll_shouldReturnPaginatedUsers() throws Exception {
    Sector sector_a = createSector("sector_a", null, 1);
    Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
    createUser("user_a_q", true, List.of(sector_a));
    createUser("user_b_q", true, List.of(sector_a, sector_b));

    mockMvc.perform(get("/users")
                    .param("pageNumber", "0")
                    .param("pageSize", "1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.content.length()").value(1))
            .andExpect(jsonPath("$.content[0].name").value("user_a_q"))
            .andExpect(jsonPath("$.content[0].agreeToTerms").value(true))
            .andExpect(jsonPath("$.content[0].sectors.length()").value(1))
            .andExpect(jsonPath("$.content[0].sectors[0]").value("sector_a"))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
            .andExpect(jsonPath("$.pageable.pageSize").value(1));

    mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.content.length()").value(2))
            .andExpect(jsonPath("$.content[1].name").value("user_b_q"))
            .andExpect(jsonPath("$.content[1].agreeToTerms").value(true))
            .andExpect(jsonPath("$.content[1].sectors.length()").value(2))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
            .andExpect(jsonPath("$.pageable.pageSize").value(100));
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
            .andDo(print())
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
    var sector_b = createSector("sector_b", sector_a.getId(), 2);
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
            .andDo(print())
            .andExpect(jsonPath("$.name").value("newquser 2"))
            .andExpect(jsonPath("$.agreeToTerms").value(true))
            .andExpect(jsonPath("$.id").value(createdUser.getId()))
            .andExpect(jsonPath("$.sectors.length()").value(1));
  }

  @Test
  void update_shouldThrowError_ifUserDoesNotExist() throws Exception {
    SaveUserDto dto = new SaveUserDto()
            .setSectorIds(List.of(3L))
            .setName("new@user 2")
            .setAgreeToTerms(true);
    byte[] bytes = getBytes(dto);

    mockMvc.perform(put("/users/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").value("given user does not exist"));
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

    mockMvc.perform(delete(String.format("/users/%s", 99)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").value("USER_NOT_EXIST"));

  }
}