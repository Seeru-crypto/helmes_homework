package demo.service.filter;

import java.util.HashMap;
import java.util.Map;

public enum DataTypes {
  STRING,
  DATE,
  NUMBER;

  public static Map<String, DataTypes> getDataMap() {
    Map<String, DataTypes> dataMap = new HashMap<>();
    dataMap.put("name", STRING);
    dataMap.put("dob", DATE);

    return dataMap;
  }

  public static boolean isStringInEnumList(String string) {
    for (DataTypes value : DataTypes.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }
}
