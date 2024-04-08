package demo.service.validation.pageable_validator;

import demo.service.validation.ValidationErrors;

public enum PageableErrors implements ValidationErrors {
  PAGE_SIZE_IS_TOO_LARGE,
  INVALID_SORT_BY_VALUE;

  @Override
  public String getKood() {
    return this.name();
  }
  public static PageableErrors getValue(String kood) { return PageableErrors.valueOf(kood);}
}
