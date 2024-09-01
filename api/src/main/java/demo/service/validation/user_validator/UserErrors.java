package demo.service.validation.user_validator;

import demo.service.validation.ValidationErrors;

public enum UserErrors implements ValidationErrors {
  NAME_NOT_UNIQUE,
  INCORRECT_LENGTH,
  USER_NOT_EXIST,
  ID_IS_NULL,
  TOS_NOT_ACCEPTED,
  USER_TOO_YOUNG,
  NAME_DOESNT_CONTAIN_Q;

  @Override
  public String getCode() {
    return this.name();
  }
  public static UserErrors getValue(String kood) { return UserErrors.valueOf(kood);}
}
