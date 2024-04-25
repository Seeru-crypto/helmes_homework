package demo.service.validation.pageable_validator;

import demo.service.validation.user_validator.UserErrors;

public enum ValidSortByValues {
  ID, // USER.ID
  NAME, // USER.NAME
  PHONENUMBER,  // USER.PHONENUMBER
  EMAIL, // USER.EMAIL
  AGREETOTERMS, // USER.AGREETOTERMS
  CREATEDAT; // USER.CREATEDAT

  public static UserErrors getValue(String kood) { return UserErrors.valueOf(kood);}

  public static boolean isStringInEnumList(String string) {
    for (ValidSortByValues value : ValidSortByValues.values()) {
      if (value.name().equals(string.toUpperCase())) {
        return true;
      }
    }
    return false;
  }
}
