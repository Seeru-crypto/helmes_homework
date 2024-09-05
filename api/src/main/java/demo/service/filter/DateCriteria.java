package demo.service.filter;

import java.util.Arrays;
import java.util.List;

public enum DateCriteria implements Filters {
    BEFORE,
    AFTER,
    EQUALS;

    @Override
    public String getCode() {
        return this.name();
    }

    public static List<String> getStringCriterias() {
        return Arrays.stream(DateCriteria.values()).map(Enum::toString).toList();
    }
}
