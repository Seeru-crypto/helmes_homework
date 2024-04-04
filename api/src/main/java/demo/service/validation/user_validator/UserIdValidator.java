package demo.service.validation.user_validator;

import demo.service.validation.ValidationResult;

public interface UserIdValidator {
  ValidationResult validate(Long id);
}
