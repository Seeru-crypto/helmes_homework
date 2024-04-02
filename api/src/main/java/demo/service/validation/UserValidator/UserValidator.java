package demo.service.validation.UserValidator;

import demo.model.User;
import demo.service.validation.ValidationResult;

public interface UserValidator {
  ValidationResult validate(User data);
}
