package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.User;
import demo.model.UserFilter;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
            .andExpect(jsonPath("$.name").value("hello world"))
            .andExpect(jsonPath("$.filters[0].type").value("type 2"))
            .andExpect(jsonPath("$.filters[0].criteria").value("criteria 2"))
            .andExpect(jsonPath("$.filters[0].value").value("value 2"))
            .andExpect(jsonPath("$.filters[1].type").value("type 1"))
            .andExpect(jsonPath("$.filters[1].criteria").value("criteria 1"))
            .andExpect(jsonPath("$.filters[1].value").value("value 1"))
    ;

    List<UserFilter> existingUserFilters = findAll(UserFilter.class);
    assertEquals(1, existingUserFilters.size());
    assertEquals("hello world", existingUserFilters.get(0).getName());

    List<Filter> existingFilters = findAll(Filter.class);
    assertEquals(2, existingFilters.size());
    assertEquals("type 2", existingFilters.get(0).getType());
  }
}