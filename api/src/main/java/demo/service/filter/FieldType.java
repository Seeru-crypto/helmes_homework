package demo.service.filter;

public enum FieldType {
  STRING,
  DATE,
  NUMBER;

  public static boolean isStringInEnumList(String string) {
    for (FieldType value : FieldType.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }
}
