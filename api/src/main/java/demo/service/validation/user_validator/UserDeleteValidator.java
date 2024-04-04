package demo.service.validation.user_validator;

import demo.model.User;
import demo.service.validation.ValidationResult;

public interface UserDeleteValidator {
  ValidationResult validate(Long id);
}
