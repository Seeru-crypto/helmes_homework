package demo.service.filter;

import demo.ContextIntegrationTest;
import demo.controller.dto.FilterDto;
import demo.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static demo.service.filter.DateCriteria.AFTER;
import static demo.service.filter.UserFieldNames.DOB;

class FilteringLogicServiceTest extends ContextIntegrationTest {
  @Test
  void filteringByDate_After_shouldReturnList() {
    var users = createDefaultUsers();
    var filters = List.of(new FilterDto().setCriteria(AFTER.getKood()).setValue("2001-05-10T21:00:25.451157400Z").setType(DataTypes.DATE).setFieldName(DOB));
    var mainUser = users.get(0);
    var userFilter = createUserFilter(filters, "user filter 1", mainUser);

    var test = filteringLogicService.findAllByFilter(userFilter, User.class);

    Assert.assertNotEquals(test.size(), 2);
  }

  @Test
  void filteringByDate_before_shouldReturnList() {


  }
  @Test
  void filteringByDate_between_shouldReturnList() {

  }
  @Test
  void filteringByDate_equals_shouldReturnList() {

  }
}