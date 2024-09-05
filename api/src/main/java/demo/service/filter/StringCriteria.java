package demo.service.filter;

import java.util.Arrays;
import java.util.List;

public enum StringCriteria implements Filters {
    CONTAINS,
    DOES_NOT_CONTAIN,
    EQUALS;

    @Override
    public String getCode() {
        return this.name();
    }

    public static List<String> getStringCriterias() {
        return Arrays.stream(StringCriteria.values()).map(Enum::toString).toList();
    }
}
