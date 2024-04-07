package demo.service.validation.sector_validator;

import demo.service.validation.ValidationErrors;

public enum SectorErrors implements ValidationErrors {
  NAME_EXISTS,
  VALUE_EXISTS;

  @Override
  public String getKood() {
    return this.name();
  }
  public static SectorErrors getValue(String kood) { return SectorErrors.valueOf(kood);}
}
