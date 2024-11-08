package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Sector;
import demo.model.User;
import demo.service.filter.FieldType;
import demo.service.filter.NumberCriteria;
import demo.service.filter.StringCriteria;
import demo.service.filter.UserFieldNames;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static demo.service.filter.DateCriteria.AFTER;
import static demo.service.filter.DateCriteria.BEFORE;
import static demo.service.filter.NumberCriteria.*;
import static demo.service.filter.StringCriteria.CONTAINS;
import static demo.service.filter.StringCriteria.DOES_NOT_CONTAIN;
import static org.hamcrest.Matchers.containsInAnyOrder;
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

    mockMvc.perform(post("/filters/{id}",users.get(0).getId() )
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bytes))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.filters.length()").value(3))
    ;
  }

  @Test
  void findByUserId_ShouldReturnUserFilters() throws Exception {
    Sector sector = createSector("sector", null, 1);
    User user = createUser(USER_NAME_1, true, List.of(sector));

    List<FilterDto> filterDtos = getFilterDtoList();
    createUserFilter(filterDtos, "new filter profile", user);

    FilterDto filter3 = new FilterDto().setCriteriaValue(StringCriteria.EQUALS.getCode())
            .setValue("value 3")
            .setFieldName(UserFieldNames.NAME)
            .setType(FieldType.STRING);
    createUserFilter(List.of(filter3), "second profile", user);

    User hiddenUser = createUser(USER_NAME_3, true, List.of(sector));
    FilterDto filter4 = new FilterDto()
            .setCriteriaValue(NumberCriteria.BIGGER_THAN.getCode())
            .setValue("2")
            .setFieldName(UserFieldNames.HEIGHT)
            .setType(FieldType.NUMBER);
    createUserFilter(List.of(filter4), "hidden profile", hiddenUser);

    mockMvc.perform(get("/filters/{id}", user.getId())
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
            .andExpect(jsonPath("$[0].criteriaValues[*]").value(containsInAnyOrder(CONTAINS.getCode(), EQUALS.getCode(), DOES_NOT_CONTAIN.getCode())))

            .andExpect(jsonPath("$[1].field").value("dob"))
            .andExpect(jsonPath("$[1].fieldType").value("DATE"))
            .andExpect(jsonPath("$[1].criteriaValues[*]").value(containsInAnyOrder(BEFORE.getCode(), AFTER.getCode(), EQUALS.getCode())))

            .andExpect(jsonPath("$[2].field").value("height"))
            .andExpect(jsonPath("$[2].fieldType").value("NUMBER"))
            .andExpect(jsonPath("$[2].criteriaValues[*]").value(containsInAnyOrder(SMALLER_THAN.getCode(), EQUALS.getCode(), BIGGER_THAN.getCode())))
    ;
  }
}