package demo.service.validation.pageable_validator;

import demo.service.validation.ValidationErrors;

public enum PageableErrors implements ValidationErrors {
  SIZE_OF_PAGE_INVALID,
  PAGE_NUMBER_INVALID,
  SORT_BY_INVALID;

  @Override
  public String getKood() {
    return this.name();
  }
  public static PageableErrors getValue(String kood) { return PageableErrors.valueOf(kood);}
}
