package demo.service.filter;

public enum NumberCriteria implements Filters {
  SMALLER_THAN,
  EQUALS,
  BIGGER_THAN;

  @Override
  public String getKood() {
    return null;
  }
}
