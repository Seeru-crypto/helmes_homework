package demo;

import com.jayway.jsonpath.JsonPath;
import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
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
                .andExpect(jsonPath("$.content[1].name").value("user_a_q"))
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
                .setName("newquser")
                .setEmail("tere@gmail.com")
                .setPhoneNumber("+372 1234567")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andReturn();

        try {
            assertStatusCode(result, 201);
            assertJsonField(result, "$.name", "newquser");
            assertJsonField(result, "$.agreeToTerms", true);
            assertJsonField(result, "$.phoneNumber", "+372 1234567");
            assertJsonField(result, "$.email", "tere@gmail.com");
            assertJsonField(result, "$.sectorNames.length()", 2);
        } catch (AssertionError e) {
            logTestResult(result, bytes);
            throw e;
        }
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
                .andExpect(jsonPath("$").value(NAME_DOESNT_CONTAIN_Q.getCode()));
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

        MvcResult result = mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andDo(print())
                .andReturn();

        try {
            assertStatusCode(result, 200);
            assertJsonField(result, "$.name", updatedName);
            assertJsonField(result, "$.agreeToTerms", true);
            assertJsonField(result, "$.id", createdUser.getId().toString());
            assertJsonField(result, "$.sectorNames.length()", 1);
        } catch (AssertionError e) {
            logTestResult(result, bytes);
            throw e;
        }
    }

    private void assertStatusCode(MvcResult result, int expectedStatus) throws UnsupportedEncodingException {
        assertEquals(expectedStatus, result.getResponse().getStatus(), "Unexpected status code");
    }

    private void assertJsonField(MvcResult result, String jsonPath, Object expectedValue) throws UnsupportedEncodingException {
        Object actualValue = JsonPath.read(result.getResponse().getContentAsString(), jsonPath);
        assertEquals(expectedValue, actualValue, "Unexpected value for JSON path: " + jsonPath);
    }

    @Test
    void update_shouldThrowError_ifUserDoesNotExist() throws Exception {
        UserDto dto = new UserDto()
                .setSectorNames(List.of("tere"))
                .setId(UUID.randomUUID())
                .setName("new@user 2")
                .setAgreeToTerms(true);
        byte[] bytes = getBytes(dto);

        mockMvc.perform(put("/users")
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

    @Test
    void findAllBySector_shouldReturnNotFound_ifSectorIdMissing() throws Exception {
        mockMvc.perform(get("/users/sector/99"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}