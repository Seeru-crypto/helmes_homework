package demo.service.validation.user_validator;

import demo.service.validation.EnumInterface;

public enum UserErrors implements EnumInterface {
  NAME_NOT_UNIQUE,
  NAME_DOESNT_CONTAIN_Q;


  @Override
  public String getKood() {
    return this.name();
  }
  public static UserErrors getValue(String kood) { return UserErrors.valueOf(kood);}

}
