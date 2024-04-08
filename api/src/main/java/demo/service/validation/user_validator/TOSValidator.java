package demo.service.validation.user_validator;

import demo.model.User;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.user_validator.UserErrors.TOS_NOT_ACCEPTED;

@Component
public class TOSValidator implements Validator<User> {
  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (Boolean.TRUE.equals(data.getAgreeToTerms())) {
      return result.setValid(true);
    }
    return result.setValid(false).setMessage(TOS_NOT_ACCEPTED);
  }
}
