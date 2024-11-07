package demo.service.filter;

public enum UserFieldNames {
  NAME,
  DOB,
  HEIGHT
  ;

  public String getCode() {
    return this.name();
  }
}
