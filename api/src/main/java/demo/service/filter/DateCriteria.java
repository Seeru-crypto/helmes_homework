package demo.service.filter;

import java.util.List;

public enum DateCriteria implements Filters {
  BEFORE,
  AFTER,
  EQUALS,
  BETWEEN;

  @Override
  public String getKood() {
    return null;
  }

  public static List<Filters> getDateCriterias() {
    return List.of(BEFORE, AFTER, EQUALS, BETWEEN);
  }
}
