package demo.service.filter;

import demo.ContextIntegrationTest;
import demo.controller.dto.FilterDto;
import demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static demo.service.filter.DateCriteria.*;
import static demo.service.filter.UserFieldNames.DOB;
import static demo.service.filter.UserFieldNames.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilteringIntegrationTest extends ContextIntegrationTest {
    User mainUser;

    @BeforeEach
    void beforeEach() {
        var users = createUsersWithoutSectors();
        mainUser = users.get(0);
    }

    @ParameterizedTest
    @MethodSource("DateCriteriaProvider")
    void filterByDate_ShouldReturnCorrectList(DateCriteria criteria, String value, int expectedSize) {
        FilterDto f1 = new FilterDto().setCriteriaValue(criteria.getCode()).setValue(value).setType(FieldType.DATE).setFieldName(DOB);
        var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

        var res = filteringLogicService.findAllByFilter(userFilter, User.class);
        assertEquals(expectedSize, res.size());
    }

    private static Stream<Arguments> DateCriteriaProvider() {
        return Stream.of(
                Arguments.of(AFTER, "2001-05-10T21:00:25.451157400Z", 2),
                Arguments.of(BEFORE, "1999-05-10T21:00:25.451157400Z", 1),
                Arguments.of(EQUALS, "1980-05-10T21:00:25.451157400Z", 0),
                Arguments.of(EQUALS, "1995-04-10T21:00:25.451157400Z", 1),
                Arguments.of(EQUALS, "1995-04-10T00:00:00.000000000Z", 1)
        );
    }

    @Test
    void filteringByDate_composite_shouldReturnList() {
        FilterDto f1 = new FilterDto().setCriteriaValue(AFTER.getCode()).setValue("1996-04-10T21:00:25.451157400Z").setType(FieldType.DATE).setFieldName(DOB);
        FilterDto f2 = new FilterDto().setCriteriaValue(BEFORE.getCode()).setValue("2009-04-10T21:00:25.451157400Z").setType(FieldType.DATE).setFieldName(DOB);
        var userFilter_v2 = createUserFilter(List.of(f1, f2), "user filter 1", mainUser);

        var test_v2 = filteringLogicService.findAllByFilter(userFilter_v2, User.class);
        assertEquals(2, test_v2.size());
        assertEquals("qJane Does", test_v2.get(0).getName());
        assertEquals("qJack Doesn't", test_v2.get(1).getName());
    }

  @ParameterizedTest
  @MethodSource("StringCriteriaProvider")
  void filterByString_ShouldReturnCorrectList(StringCriteria criteria, String value, int expectedSize) {
    FilterDto f1 = new FilterDto().setCriteriaValue(criteria.getCode()).setValue(value).setType(FieldType.STRING).setFieldName(NAME);
    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
    assertEquals(expectedSize, res.size());
  }

  private static Stream<Arguments> StringCriteriaProvider() {
    return Stream.of(
            Arguments.of(StringCriteria.CONTAINS, "Does", 3),
            Arguments.of(StringCriteria.CONTAINS, "abcd", 0),
            Arguments.of(StringCriteria.DOES_NOT_CONTAIN, "Does", 1),
            Arguments.of(StringCriteria.EQUALS, "qJack Doesn't", 1),
            Arguments.of(StringCriteria.EQUALS, "qJack Doesn", 0)
    );
  }

    @Test
    void filteringByString_composite_shouldReturnList() {
        FilterDto f1 = new FilterDto().setCriteriaValue(StringCriteria.CONTAINS.getCode()).setValue("Does").setType(FieldType.STRING).setFieldName(NAME);
        FilterDto f2 = new FilterDto().setCriteriaValue(StringCriteria.DOES_NOT_CONTAIN.getCode()).setValue("qJane").setType(FieldType.STRING).setFieldName(NAME);
        var userFilter_v2 = createUserFilter(List.of(f1, f2), "user filter 1", mainUser);

        var test_v2 = filteringLogicService.findAllByFilter(userFilter_v2, User.class);
        assertEquals(2, test_v2.size());
        assertEquals("qJohn Does", test_v2.get(0).getName());
        assertEquals("qJack Doesn't", test_v2.get(1).getName());
    }
}