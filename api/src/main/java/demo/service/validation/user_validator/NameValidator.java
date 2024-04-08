package demo.service.validation.user_validator;

import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static demo.model.User.USER_MAX_NAME_LENGTH;
import static demo.model.User.USER_MIN_NAME_LENGTH;
import static demo.service.validation.user_validator.UserErrors.INCORRECT_LENGTH;
import static demo.service.validation.user_validator.UserErrors.NAME_DOESNT_CONTAIN_Q;
import static demo.service.validation.user_validator.UserErrors.NAME_NOT_UNIQUE;

@Component
public class NameValidator implements Validator<User> {

  private final UserRepository userRepository;

  @Autowired
  public NameValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (!(USER_MIN_NAME_LENGTH <= data.getName().length() && data.getName().length() < USER_MAX_NAME_LENGTH)) {
      return result.setValid(false).setMessage(INCORRECT_LENGTH);
    }

    if (!(data.getName().contains("q"))) {
      return result.setValid(false).setMessage(NAME_DOESNT_CONTAIN_Q);
    }

    if (!isNameUnique(data.getName())) {
      return result.setValid(false).setMessage(NAME_NOT_UNIQUE);
    }
    return result.setValid(true);
  }

  private boolean isNameUnique(String name) {
    return !userRepository.existsByName(name);
  }
}
