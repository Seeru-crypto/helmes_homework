package demo.service.validation.user_validator;

import demo.model.User;
import demo.repository.UserRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static demo.service.validation.user_validator.UserErrors.PHONE_NR_NOT_UNIQUE;

@Component
public class PhoneNumberValidator implements Validator<User> {

  private final UserRepository userRepository;

  @Autowired
  public PhoneNumberValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();

    if (userRepository.existsByPhoneNumber(data.getPhoneNumber()) && !Objects.equals(data.getPhoneNumber(), "")) {
      return result.setValid(false).setMessage(PHONE_NR_NOT_UNIQUE);
    }

    return result.setValid(true);
  }
}
