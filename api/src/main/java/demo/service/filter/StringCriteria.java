package demo.service.filter;

import java.util.List;

public enum StringCriteria implements Filters {
  CONTAINS,
  DOES_NOT_CONTAIN,
  EQUALS;

  @Override
  public String getKood() {
    return null;
  }

  public static List<Filters> getStringCriterias() {
    return List.of(CONTAINS, EQUALS, DOES_NOT_CONTAIN);
  }
}
