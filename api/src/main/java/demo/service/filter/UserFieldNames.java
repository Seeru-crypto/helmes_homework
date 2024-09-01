package demo.service.filter;

public enum UserFieldNames {
  NAME,
  DOB,
  ;

  public String getCode() {
    return this.name();
  }
}
