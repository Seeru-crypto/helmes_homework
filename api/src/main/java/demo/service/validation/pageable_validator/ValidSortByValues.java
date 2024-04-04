package demo.service.validation.pageable_validator;

import demo.service.validation.user_validator.UserErrors;

public enum ValidSortByValues {
  ID,
  NAME,
  PHONENUMBER,
  EMAIL,
  AGREETOTERMS;


  public static UserErrors getValue(String kood) { return UserErrors.valueOf(kood);}
}
