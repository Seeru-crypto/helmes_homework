package demo;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class UserIntegrationTest extends ContextIntegrationTest {
    @Test
    void findAll_shouldReturnPaginatedUsers() throws Exception {
        Sector sector_a = createSector("sector_a", null);
        createSector("sector_b", sector_a.getId());
        createUser("user_a_@", true, List.of("sector_a"));
        createUser("user_b_@", true, List.of("sector_a", "sector_b"));

        mockMvc.perform(get("/users")
                        .param("pageNumber", "0")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value("user_a_@"))
                .andExpect(jsonPath("$.content[0].agreeToTerms").value(true))
                .andExpect(jsonPath("$.content[0].sectors.length()").value(1))
                .andExpect(jsonPath("$.content[0].sectors[0].name").value("sector_a"))
                .andExpect(jsonPath("$.content[0].sectors[0].children").isEmpty())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(1));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[1].name").value("user_b_@"))
                .andExpect(jsonPath("$.content[1].agreeToTerms").value(true))
                .andExpect(jsonPath("$.content[1].sectors.length()").value(2))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(10));
    }

    @Test
    void save_shouldSaveANewUSer() throws Exception {
        Sector sector_a = createSector("sector_a", null);
        createSector("sector_b", sector_a.getId());

        SaveUserDto dto = new SaveUserDto()
                .setSectors(List.of("sector_a", "sector_b"))
                .setName("new@user")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("new@user"))
                .andExpect(jsonPath("$.agreeToTerms").value(true))
                .andExpect(jsonPath("$.sectors.length()").value(2));
    }

    @Test
    void save_shouldThrowError_ifNameIncorrect() throws Exception {
        Sector sector_a = createSector("sector_a", null);
        createSector("sector_b", sector_a.getId());

        SaveUserDto dto = new SaveUserDto()
                .setSectors(List.of("sector_a", "sector_b"))
                .setName("Aasdasdas")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("does not contain @"));
    }

    @Test
    void save_shouldThrowError_ifNameExists() throws Exception {
        Sector sector_a = createSector("sector_a", null);
        createSector("sector_b", sector_a.getId());
        createUser("user_a_@", true, List.of("sector_a", "sector_b"));

        SaveUserDto dto = new SaveUserDto()
                .setSectors(List.of("sector_a", "sector_b"))
                .setName("user_a_@")
                .setAgreeToTerms(true);

        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Name is not unique"));
    }

    @Test
    void update_shouldUpdateExistingUser() throws Exception {
        Sector sector_a = createSector("sector_a", null);
        createSector("sector_b", sector_a.getId());
        User createdUser = createUser("user_a_@", true, List.of("sector_a", "sector_b"));

        SaveUserDto dto = new SaveUserDto()
                .setSectors(List.of("sector_b"))
                .setName("new@user 2")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);

        mockMvc.perform(put(String.format("/users/%s", createdUser.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("new@user 2"))
                .andExpect(jsonPath("$.agreeToTerms").value(true))
                .andExpect(jsonPath("$.sectors.length()").value(1));
    }

    @Test
    void update_shouldThrowError_ifUserDoesNotExist() throws Exception {
        Sector sector_a = createSector("sector_a", null);
        createSector("sector_b", sector_a.getId());

        SaveUserDto dto = new SaveUserDto()
                .setSectors(List.of("sector_b"))
                .setName("new@user 2")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);

        mockMvc.perform(put("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$").value("given user does not exist"));
    }
}