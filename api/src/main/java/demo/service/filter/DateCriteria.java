package demo.service.filter;

import java.util.List;

public enum DateCriteria implements Filters {
  BEFORE,
  AFTER,
  EQUALS,
  BETWEEN;

  @Override
public String getKood() {
    return this.name();
  }

  public static boolean isStringInEnumList(String string) {
    for (DateCriteria value : DateCriteria.values()) {
      try {
        if (value.name().equals(string.toUpperCase())) {
          return true;
        }
      } catch (NullPointerException e) {
        return false;
      }
    }
    return false;
  }
  public static List<Filters> getDateCriterias() {
    return List.of(BEFORE, AFTER, EQUALS, BETWEEN);
  }
}
