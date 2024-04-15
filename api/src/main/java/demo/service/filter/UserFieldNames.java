package demo.service.filter;

import java.util.HashMap;
import java.util.Map;

public enum UserFieldNames {
  NAME,
  DOB
  ;

  // TODO: Implement dataMap validation for filters
  public static Map<UserFieldNames, DataTypes> getDataMap() {
    Map<UserFieldNames, DataTypes> dataMap = new HashMap<>();
    dataMap.put(NAME, DataTypes.STRING);
    dataMap.put(DOB, DataTypes.DATE);
    return dataMap;
  }

  public static boolean isStringInEnumList(String string) {
    for (UserFieldNames value : UserFieldNames.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }
}
