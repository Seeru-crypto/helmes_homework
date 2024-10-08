package demo.service.validation.user_validator;

import demo.model.User;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static demo.service.validation.user_validator.UserErrors.USER_TOO_YOUNG;
import static java.time.temporal.ChronoUnit.YEARS;

@Component
public class DateValidator implements Validator<User> {

  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (data.getDob() == null) {
      return result.setValid(true);
    }
    return result.setValid(true);
  }
}
