package demo.service.validation.user_validator;

import demo.model.User;
import demo.service.validation.ValidationResult;
import org.springframework.stereotype.Component;

import static demo.exception.BusinessException.USER_TOS;

@Component
public class UserTOSUserValidator implements UserValidator {

  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (Boolean.TRUE.equals(data.getAgreeToTerms())) {
      return result.setValid(true);
    }
    return result.setValid(false).setMessage(USER_TOS);
  }
}
