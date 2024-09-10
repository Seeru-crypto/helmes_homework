package demo.service.filter;

import java.util.Arrays;

public interface Filters {
  String getCode();

  static <E extends Enum<E>> boolean isStringInEnumList(String string, Class<E> enumClass) {
    return Arrays.stream(enumClass.getEnumConstants())
            .map(Enum::name)
            .anyMatch(name -> name.equalsIgnoreCase(string));
  }
}
