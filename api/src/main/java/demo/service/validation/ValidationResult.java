package demo.service.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResult {
  boolean isValid;
  String message;
}
