package demo;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

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
        createUser(USER_NAME_2, true, List.of(sector_a));
        createUser(USER_NAME_1, true, List.of(sector_a, sector_b));

        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("sort", "name,asc")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value(USER_NAME_2))
                .andExpect(jsonPath("$.content[0].agreeToTerms").value(true))
                .andExpect(jsonPath("$.content[0].sectorNames.length()").value(1))
                .andExpect(jsonPath("$.content[0].sectorNames[0]").value("sector_a"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(1));

        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("sort", "name,desc")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[1].name").value(USER_NAME_2))
                .andExpect(jsonPath("$.content[1].agreeToTerms").value(true))
                .andExpect(jsonPath("$.content[1].sectorNames.length()").value(1))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(10));
    }

    @Test
    void save_shouldSaveANewUser() throws Exception {
        Sector sector_a = createSector("sector_a", null, 1);
        createSector("sector_b", sector_a.getId(), 2);

        SaveUserDto dto = new SaveUserDto()
                .setSectorIds(List.of(1L, 2L))
                .setName(USER_NAME_1)
                .setEmail(USER_EMAIL_1)
                .setPhoneNumber(USER_PHONE_NUMBER_1)
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(USER_NAME_1))
                .andExpect(jsonPath("$.agreeToTerms").value(true))
                .andExpect(jsonPath("$.phoneNumber").value(USER_PHONE_NUMBER_1))
                .andExpect(jsonPath("$.email").value(USER_EMAIL_1))
                .andExpect(jsonPath("$.sectorNames.length()").value(2));
    }

    @Test
    void save_shouldThrowError_ifNameIncorrect() throws Exception {
        Sector sector_a = createSector("sector_a", null, 1);
        createSector("sector_b", sector_a.getId(), 2);

        SaveUserDto dto = new SaveUserDto()
                .setSectorIds(List.of(1L, 2L))
                .setName("abc")
                .setAgreeToTerms(true);

        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(NAME_DOESNT_CONTAIN_Q.getCode()));
    }

    @Test
    void save_shouldThrowError_ifNameExists() throws Exception {
        Sector sector_a = createSector("sector_a", null, 1);
        Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
        createUser(USER_NAME_3, true, List.of(sector_a, sector_b));

        SaveUserDto dto = new SaveUserDto()
                .setSectorIds(List.of(sector_a.getId(), sector_b.getId()))
                .setName(USER_NAME_3)
                .setAgreeToTerms(true);

        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(NAME_NOT_UNIQUE.getCode()));
    }

    @Test
    void save_shouldThrowError_ifIdExists() throws Exception {
        Sector sector_a = createSector("sector_a", null, 1);
        Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
        createUser(USER_NAME_4, true, List.of(sector_a, sector_b));

        SaveUserDto dto = new SaveUserDto()
                .setSectorIds(List.of(sector_a.getId(), sector_b.getId()))
                .setName(USER_NAME_4)
                .setAgreeToTerms(true);

        byte[] bytes = getBytes(dto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(NAME_NOT_UNIQUE.getCode()));
    }

    @Test
    void update_shouldUpdateExistingUser() throws Exception {
        Sector sector_a = createSector("sector_a", null, 1);
        Sector sector_b = createSector("sector_b", sector_a.getId(), 2);
        User createdUser = createUser("initial nameQ", true, List.of(sector_b, sector_a));

        String updatedName = "updated nameQ";
        UserDto dto = new UserDto()
                .setSectorNames(List.of(sector_a.getName()))
                .setName(updatedName)
                .setId(createdUser.getId())
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);

        var url = "/users/" + createdUser.getId();
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.agreeToTerms").value(true))
                .andExpect(jsonPath("$.id").value(createdUser.getId().toString()))
                .andExpect(jsonPath("$.sectorNames.length()").value(1))
        ;
    }

    @Test
    void update_shouldThrowError_ifUserDoesNotExist() throws Exception {
        UserDto dto = new UserDto()
                .setSectorNames(List.of("tere"))
                .setId(UUID.randomUUID())
                .setName("new@user 2")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);

        mockMvc.perform(put("/users/2edb3143-acb1-4883-96a5-58e96e3b2639")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldDeleteUser_byId() throws Exception {
        Sector sector_a = createSector("sector_a", null, 1);
        User existingUser = createUser(USER_NAME_1, true, List.of(sector_a));

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
        createFullSectorTree();
        var users = createDefaultUsers();

        mockMvc.perform(get("/users/sector/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].sectorNames.length()").value(users.get(0).getSectors().size()))
                .andExpect(jsonPath("$[0].sectorNames[0]").value("Manufacturing"))
                .andExpect(jsonPath("$[1].name").value(users.get(3).getName()))
                .andExpect(jsonPath("$[1].sectorNames.length()").value(users.get(3).getSectors().size()))
                .andExpect(jsonPath("$[1].sectorNames[*]").value(containsInAnyOrder("Manufacturing", "Project furniture")))
        ;
    }

    // TODO: fix resolve the technical question with Markus
//    @Test
//    void findAllBySector_shouldReturnNotFound_ifSectorIdMissing() throws Exception {
//        mockMvc.perform(get("/users/sector/99"))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
}