package demo.service.validation.user_validator;

import demo.service.validation.ValidationErrors;

public enum UserErrors implements ValidationErrors {
  NAME_NOT_UNIQUE,
  INCORRECT_LENGTH,
  TOS_NOT_ACCEPTED,
  NAME_DOESNT_CONTAIN_Q;

  @Override
  public String getKood() {
    return this.name();
  }
  public static UserErrors getValue(String kood) { return UserErrors.valueOf(kood);}
}
