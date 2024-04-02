package demo.service.validation;

import demo.model.User;

public interface Validator {
  ValidationResult validate(User data);
}
