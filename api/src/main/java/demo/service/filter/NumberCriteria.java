package demo.service.filter;

import java.util.Arrays;
import java.util.List;

public enum NumberCriteria implements Filters {
  SMALLER_THAN,
  EQUALS,
  BIGGER_THAN;

  @Override
public String getCode() {
    return this.name();
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

  public static List<String> getStringCriterias() {
    return Arrays.stream(StringCriteria.values()).map(Enum::toString).toList();
  }
}
