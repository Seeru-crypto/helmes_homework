package demo.service.filter;

public enum DateCriteria implements Filters {
  BEFORE,
  AFTER,
  EQUALS,
  BETWEEN;

  @Override
  public String getKood() {
    return null;
  }
}
