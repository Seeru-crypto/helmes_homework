package demo.service.validation.user_validator;

import demo.model.User;
import demo.service.validation.ValidationResult;

public interface UserValidator {
  ValidationResult validate(User data);
}
