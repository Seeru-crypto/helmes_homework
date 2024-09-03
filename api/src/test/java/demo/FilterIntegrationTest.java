package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.Sector;
import demo.model.User;
import demo.model.UserFilter;
import demo.service.filter.FieldType;
import demo.service.filter.NumberCriteria;
import demo.service.filter.StringCriteria;
import demo.service.filter.UserFieldNames;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FilterIntegrationTest extends ContextIntegrationTest {

  @Test
  void save_shouldSaveNewFilter() throws Exception {
    createFullSectorTree();
    List<User> users = createDefaultUsers();

    List<FilterDto> filterDtos = getFilterDtoList();
    UserFilterDto requestDto = new UserFilterDto().setName("hello world").setFilters(filterDtos);
    byte[] bytes = getBytes(requestDto);

    mockMvc.perform(post(String.format("/filters/%s", users.get(0).getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("hello world"))
            .andExpect(jsonPath("$.filters[*].type").value(containsInAnyOrder("STRING", "DATE", "NUMBER")))
            .andExpect(jsonPath("$.filters[*].criteriaValue").value(containsInAnyOrder("CONTAINS", "AFTER", "SMALLER_THAN")))
            .andExpect(jsonPath("$.filters[*].value").value(containsInAnyOrder(filterDtos.stream().map(FilterDto::getValue).toArray())))
    ;

    List<UserFilter> existingUserFilters = findAll(UserFilter.class);
    assertEquals(1, existingUserFilters.size());
    assertEquals("hello world", existingUserFilters.get(0).getName());

    List<Filter> existingFilters = findAll(Filter.class);
    assertEquals(3, existingFilters.size());
    assertEquals(FieldType.STRING, existingFilters.get(0).getType());
  }

  @Test
  void findByUserId_ShouldReturnUserFilters() throws Exception {
    Sector sector = createSector("sector", null, 1);
    User user = createUser("userq", true, List.of(sector));

    List<FilterDto> filterDtos = getFilterDtoList();
    createUserFilter(filterDtos, "new filter profile", user);

    FilterDto filter3 = new FilterDto().setCriteriaValue(StringCriteria.EQUALS.getCode())
            .setValue("value 3")
            .setFieldName(UserFieldNames.NAME)
            .setType(FieldType.STRING);
    createUserFilter(List.of(filter3), "second profile", user);

    User hiddenUser = createUser("hidden_user_q", true, List.of(sector));
    FilterDto filter4 = new FilterDto()
            .setCriteriaValue(NumberCriteria.BIGGER_THAN.getCode())
            .setValue("2")
            .setFieldName(UserFieldNames.NAME)
            .setType(FieldType.NUMBER);
    createUserFilter(List.of(filter4), "hidden profile", hiddenUser);

    List<UserFilter> existingFilters = findAll(UserFilter.class);
    assertEquals(3, existingFilters.size());

    List<User> existingUsers = findAll(User.class);
    assertEquals(2, existingUsers.size());

    String path = String.format("/filters/%s", user.getId());
    mockMvc.perform(get(path)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("new filter profile", "second profile")))
            .andExpect(jsonPath("$[0].filters.length()").value(3))
            .andExpect(jsonPath("$[0].filters[*].type").value(containsInAnyOrder("DATE", "STRING", "NUMBER")))
            .andExpect(jsonPath("$[0].filters[*].criteriaValue").value(containsInAnyOrder("CONTAINS", "AFTER", "SMALLER_THAN")))
            .andExpect(jsonPath("$[0].filters[*].value").value(containsInAnyOrder(filterDtos.stream().map(FilterDto::getValue).toArray())))

            .andExpect(jsonPath("$[1].filters.length()").value(1))
            .andExpect(jsonPath("$[1].filters[0].type").value("STRING"))
    ;
  }

  @Test
  void findAllOptions_shouldReturnFilteringOptions() throws Exception {

    mockMvc.perform(get("/filters")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].field").value("name"))
            .andExpect(jsonPath("$[0].fieldType").value("STRING"))
            .andExpect(jsonPath("$[0].criteriaValues[*]").value(containsInAnyOrder("CONTAINS", "EQUALS", "DOES_NOT_CONTAIN")))
            .andExpect(jsonPath("$[1].field").value("dob"))
            .andExpect(jsonPath("$[1].fieldType").value("DATE"))
            .andExpect(jsonPath("$[1].criteriaValues[*]").value(containsInAnyOrder("BEFORE", "AFTER", "EQUALS")))
    ;
  }
}