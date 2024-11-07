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

    public static List<String> getStringCriterias() {
        return Arrays.stream(NumberCriteria.values()).map(Enum::toString).toList();
    }
}
