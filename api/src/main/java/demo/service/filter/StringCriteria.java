package demo.service.filter;

public enum StringCriteria implements Filters {
  CONTAINS,
  DOES_NOT_CONTAIN,
  EQUALS;

  @Override
  public String getKood() {
    return null;
  }
}
