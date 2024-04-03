package demo;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static demo.service.validation.user_validator.UserErrors.NAME_DOESNT_CONTAIN_Q;
import static demo.service.validation.user_validator.UserErrors.NAME_NOT_UNIQUE;
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
            .andExpect(jsonPath("$.content.length()").value(1))
            .andExpect(jsonPath("$.content[0].name").value("user_a_q"))
            .andExpect(jsonPath("$.content[0].agreeToTerms").value(true))
            .andExpect(jsonPath("$.content[0].sectorIds.length()").value(1))
            .andExpect(jsonPath("$.content[0].sectorIds[0]").value(1))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
            .andExpect(jsonPath("$.pageable.pageSize").value(1));

    mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.content.length()").value(2))
            .andExpect(jsonPath("$.content[1].name").value("user_b_q"))
            .andExpect(jsonPath("$.content[1].agreeToTerms").value(true))
            .andExpect(jsonPath("$.content[1].sectorIds.length()").value(2))
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
            .setAgreeToTerms(true);
    byte[] bytes = getBytes(dto);
    mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isCreated())
            .andDo(print())
            .andExpect(jsonPath("$.name").value("newquser"))
            .andExpect(jsonPath("$.agreeToTerms").value(true))
            .andExpect(jsonPath("$.sectorIds.length()").value(2));
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
            .andExpect(jsonPath("$.name").value("newquser 2"))
            .andExpect(jsonPath("$.agreeToTerms").value(true))
            .andExpect(jsonPath("$.sectorIds.length()").value(1));
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
}