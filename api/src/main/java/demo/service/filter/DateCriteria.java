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

  public static boolean isStringInEnumList(String string) {
    for (DateCriteria value : DateCriteria.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }


  public static List<Filters> getDateCriterias() {
    return List.of(BEFORE, AFTER, EQUALS, BETWEEN);
  }



}
