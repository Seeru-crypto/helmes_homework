package demo.service.validation.UserValidator;

import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static demo.model.User.USER_MAX_NAME_LENGTH;
import static demo.model.User.USER_MIN_NAME_LENGTH;

@Component
public class UserNameUserValidator implements UserValidator {

  private final UserRepository userRepository;

  @Autowired
  public UserNameUserValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (!(USER_MIN_NAME_LENGTH <= data.getName().length() && data.getName().length() < USER_MAX_NAME_LENGTH)) {
      return result.setResult(false).setMessage("incorrect length");
    }

    if (!(data.getName().contains("@"))) {
      return result.setResult(false).setMessage("does not contain @");
    }
    if (!isNameUnique(data.getName())) {
      return result.setResult(false).setMessage("Name is not unique");
    }
    return result.setResult(true);
  }

  private boolean isNameUnique(String name) {
    return !userRepository.existsByName(name);
  }
}
