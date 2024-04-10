package demo.service.validation.filter_validator;

import demo.service.validation.ValidationErrors;

public enum FilterErrors implements ValidationErrors {
  FILTER_ERROR,
  DEFAULT_ERROR,
  CRITERIA_ERROR

  ;
  @Override
  public String getKood() {
    return this.name();
  }
  public static FilterErrors getValue(String kood) { return FilterErrors.valueOf(kood);}
}
