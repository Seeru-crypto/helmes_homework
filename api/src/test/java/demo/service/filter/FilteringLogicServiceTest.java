package demo.service.filter;

import demo.ContextIntegrationTest;
import demo.controller.dto.FilterDto;
import demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static demo.service.filter.DateCriteria.AFTER;
import static demo.service.filter.DateCriteria.BEFORE;
import static demo.service.filter.DateCriteria.EQUALS;
import static demo.service.filter.UserFieldNames.DOB;
import static demo.service.filter.UserFieldNames.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilteringLogicServiceTest extends ContextIntegrationTest {
  User mainUser;

  @BeforeEach
  void beforeEach() {
    var users = createDefaultUsers();
    mainUser = users.get(0);
  }

  // DATE
  @Test
  void filteringByDate_After_shouldReturnList() {
    FilterDto f1 = new FilterDto().setCriteria(AFTER.getKood()).setValue("2001-05-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
    var filters = List.of(f1);

    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    var test = filteringLogicService.findAllByFilter(userFilter, User.class);

    assertEquals(test.size(), 2);
    assertEquals(test.get(0).getName(), "qJack Doesn't");
    assertEquals(test.get(1).getName(), "qJames Memorial");
  }

  @Test
  void filteringByDate_before_shouldReturnList() {
    FilterDto f1 = new FilterDto().setCriteria(BEFORE.getKood()).setValue("1999-05-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
    var filters = List.of(f1);

    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    var test = filteringLogicService.findAllByFilter(userFilter, User.class);

    assertEquals(1, test.size());
    assertEquals("qJohn Does", test.get(0).getName());
  }

  @Test
  void filteringByDate_between_shouldReturnList() {
//    FilterDto f1 = new FilterDto().setCriteria(BETWEEN.getKood()).setValue("1980-05-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
//    var filters = List.of(f1);
//
//    var userFilter = createUserFilter(filters, "user filter 1", mainUser);
//
//    var test = filteringLogicService.findAllByFilter(userFilter, User.class);
//
//    assertEquals(test.size(), 1);
//    assertEquals(test.get(0).getName(), "qJohn Does");
  }


  @Test
  void filteringByDate_equals_shouldReturnEmptyList() {
    FilterDto f1 = new FilterDto().setCriteria(EQUALS.getKood()).setValue("1980-05-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
    var filters = List.of(f1);

    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    var test = filteringLogicService.findAllByFilter(userFilter, User.class);

    assertEquals(0, test.size());
  }

  @Test
  void filteringByDate_equals_shouldReturnList() {
    FilterDto f2 = new FilterDto().setCriteria(EQUALS.getKood()).setValue("1995-04-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
    var userFilter_v2 = createUserFilter(List.of(f2), "user filter 1", mainUser);

    var test_v2 = filteringLogicService.findAllByFilter(userFilter_v2, User.class);
    assertEquals(1, test_v2.size());
    assertEquals("qJohn Does", test_v2.get(0).getName());


    FilterDto f3 = new FilterDto().setCriteria(EQUALS.getKood()).setValue("1995-04-10T00:00:00.000000000Z").setType(DataTypes.DATE).setFieldName(DOB);
    var userFilter_v3 = createUserFilter(List.of(f3), "user filter 1", mainUser);

    var test_v3 = filteringLogicService.findAllByFilter(userFilter_v3, User.class);
    assertEquals(1, test_v3.size());
    assertEquals("qJohn Does", test_v3.get(0).getName());
  }

  @Test
  void filteringByDate_composite_shouldReturnList() {
    FilterDto f1 = new FilterDto().setCriteria(AFTER.getKood()).setValue("1996-04-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
    FilterDto f2 = new FilterDto().setCriteria(BEFORE.getKood()).setValue("2009-04-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB);
    var userFilter_v2 = createUserFilter(List.of(f1, f2), "user filter 1", mainUser);

    var test_v2 = filteringLogicService.findAllByFilter(userFilter_v2, User.class);
    assertEquals(2, test_v2.size());
    assertEquals("qJane Does", test_v2.get(0).getName());
    assertEquals("qJack Doesn't", test_v2.get(1).getName());
  }

  // STRING
  @Test
  void filteringByString_contains_shouldReturnList() {
    FilterDto f1 = new FilterDto().setCriteria(StringCriteria.CONTAINS.getKood()).setValue("Does").setType(DataTypes.STRING).setFieldName(NAME);
    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
    assertEquals(3, res.size());
  }

  @Test
  void filteringByString_contains_shouldReturnEmptyList() {
    FilterDto f1 = new FilterDto().setCriteria(StringCriteria.CONTAINS.getKood()).setValue("abcd").setType(DataTypes.STRING).setFieldName(NAME);
    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
    assertEquals(0, res.size());
  }

  @Test
  void filteringByString_does_not_contain_shouldReturnList() {
    FilterDto f1 = new FilterDto().setCriteria(StringCriteria.DOES_NOT_CONTAIN.getKood()).setValue("Does").setType(DataTypes.STRING).setFieldName(NAME);
    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
    assertEquals(1, res.size());
  }

  @Test
  void filteringByString_equals_shouldReturnList() {
    FilterDto f1 = new FilterDto().setCriteria(StringCriteria.EQUALS.getKood()).setValue("qJack Doesn't").setType(DataTypes.STRING).setFieldName(NAME);
    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
    assertEquals(1, res.size());
  }

  @Test
  void filteringByString_equals_shouldEmptyList() {
    FilterDto f1 = new FilterDto()
            .setCriteria(StringCriteria.EQUALS.getKood())
            .setValue("qJack Doesn")
            .setType(DataTypes.STRING)
            .setFieldName(NAME);
    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);

    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
    assertEquals(0, res.size());
  }

//  @ParameterizedTest
//  @MethodSource("filterCriteriaProvider")
//  void filterByString_ShouldReturnCorrectList(StringCriteria criteria, String value, int expectedSize) {
//    FilterDto f1 = new FilterDto().setCriteria(criteria.getKood()).setValue(value).setType(DataTypes.STRING).setFieldName(NAME);
//    var userFilter = createUserFilter(List.of(f1), "user filter 1", mainUser);
//
//    var res = filteringLogicService.findAllByFilter(userFilter, User.class);
//    assertEquals(expectedSize, res.size());
//  }
//
//  private static Stream<Arguments> filterCriteriaProvider() {
//    return Stream.of(
//            Arguments.of(StringCriteria.CONTAINS, "Does", 3),
//            Arguments.of(StringCriteria.CONTAINS, "abcd", 0),
//            Arguments.of(StringCriteria.DOES_NOT_CONTAIN, "Does", 1),
//            Arguments.of(StringCriteria.EQUALS, "qJack Doesn't", 1),
//            Arguments.of(StringCriteria.EQUALS, "qJack Doesn", 0)
//    );
//  }

}