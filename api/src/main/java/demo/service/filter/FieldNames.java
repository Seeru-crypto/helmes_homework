package demo.service.filter;

import java.util.HashMap;
import java.util.Map;

public enum FieldNames {
  NAME,
  DOB
  ;

  public static Map<FieldNames, DataTypes> getDataMap() {
    Map<FieldNames, DataTypes> dataMap = new HashMap<>();
    dataMap.put(NAME, DataTypes.STRING);
    dataMap.put(DOB, DataTypes.DATE);
    return dataMap;
  }

  public static boolean isStringInEnumList(String string) {
    for (FieldNames value : FieldNames.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }
}
