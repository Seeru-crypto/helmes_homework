package demo.service.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResult {
  private boolean isValid;
  private ValidationErrors message;
  private String failedField;
}
