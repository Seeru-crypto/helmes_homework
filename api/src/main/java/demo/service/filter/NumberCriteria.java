package demo.service.filter;

import java.util.List;

public enum NumberCriteria implements Filters {
  SMALLER_THAN,
  EQUALS,
  BIGGER_THAN;

  @Override
  public String getKood() {
    return null;
  }

  public static boolean isStringInEnumList(String string) {
    for (NumberCriteria value : NumberCriteria.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }

  public static List<Filters> getNumberCriterias() {
    return List.of(SMALLER_THAN, EQUALS, BIGGER_THAN);
  }
}
