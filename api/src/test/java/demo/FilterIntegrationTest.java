package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FilterIntegrationTest extends ContextIntegrationTest {
    @Test
    void findAll_shouldReturnAllSectors() throws Exception {
        List<User> users = createDefaultUsers();

        FilterDto filter1 = new FilterDto().setCriteria("criteria 1").setValue("value 1").setType("type 1");
        FilterDto filter2 = new FilterDto().setCriteria("criteria 2").setValue("value 2").setType("type 2");
        List<FilterDto> filterDtos = List.of(filter2, filter1);
        UserFilterDto requestDto = new UserFilterDto().setName("hello world").setFilters(filterDtos);
        byte[] bytes = getBytes(requestDto);

        mockMvc.perform(post(String.format("/filters/%s", users.get(0).getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("tere"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.parentId").isEmpty())
                .andExpect(jsonPath("$.value").value(2))
                .andExpect(jsonPath("$.children.length()").value(0))
        ;

    }
}