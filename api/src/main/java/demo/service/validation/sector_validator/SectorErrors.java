package demo.service.validation.sector_validator;

import demo.service.validation.ValidationErrors;

public enum SectorErrors implements ValidationErrors {
  NAME_EXISTS,
  INCORRECT_LENGTH,
  VALUE_EXISTS;

  @Override
  public String getCode() {
    return this.name();
  }
  public static SectorErrors getValue(String kood) { return SectorErrors.valueOf(kood);}
}
