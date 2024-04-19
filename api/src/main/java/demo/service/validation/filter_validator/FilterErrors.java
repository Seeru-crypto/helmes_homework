package demo.service.validation.filter_validator;

import demo.service.validation.ValidationErrors;

public enum FilterErrors implements ValidationErrors {
  FILTER_ERROR,
  MAX_FILTER_LENGTH_ERROR,
  MIN_FILTER_LENGTH_ERROR,
  DEFAULT_ERROR,
  INVALID_CRITERIA_ERROR,
  INVALID_DATE_VALUE,
  INVALID_NUMBER_VALUE
  ;

  @Override
  public String getKood() {
    return this.name();
  }
  public static FilterErrors getValue(String kood) { return FilterErrors.valueOf(kood);}
}
