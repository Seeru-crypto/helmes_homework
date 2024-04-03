package demo.service.validation.user_validator;

import demo.model.User;
import demo.service.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class UserTOSUserValidator implements UserValidator {
  public static final String USER_TOS = "User must agree to terms of service";


  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (Boolean.TRUE.equals(data.getAgreeToTerms())) {
      return result.setValid(true);
    }
    return result.setValid(false).setMessage(USER_TOS);
  }
}
