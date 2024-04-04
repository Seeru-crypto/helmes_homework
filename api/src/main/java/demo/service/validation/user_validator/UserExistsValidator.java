package demo.service.validation.user_validator;

import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static demo.service.validation.user_validator.UserErrors.ID_IS_NULL;
import static demo.service.validation.user_validator.UserErrors.USER_NOT_EXIST;

@Component
public class UserExistsValidator implements UserIdValidator {

  private final UserRepository userRepository;

  @Autowired
  public UserExistsValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ValidationResult validate(Long id) {
    ValidationResult result = new ValidationResult();

    if (id == null) {
      return result.setValid(false).setMessage(ID_IS_NULL);
    }
    if (!userRepository.existsById(id)) {
      return result.setValid(false).setMessage(USER_NOT_EXIST);
    }
    return result.setValid(true);
  }
}
