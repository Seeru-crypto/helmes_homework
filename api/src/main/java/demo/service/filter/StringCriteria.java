package demo.service.filter;

import java.util.List;

public enum StringCriteria implements Filters {
    CONTAINS,
    DOES_NOT_CONTAIN,
    EQUALS;

    @Override
    public String getKood() {
        return this.name();
    }

    public static boolean isStringInEnumList(String string) {
        for (StringCriteria value : StringCriteria.values()) {
            if (value.name().equals(string.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static List<Filters> getStringCriterias() {
        return List.of(CONTAINS, EQUALS, DOES_NOT_CONTAIN);
    }
}
