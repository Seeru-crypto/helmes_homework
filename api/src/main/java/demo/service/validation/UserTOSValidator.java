package demo.service.validation;

import demo.model.User;
import org.springframework.stereotype.Component;

import static demo.exception.BusinessException.USER_TOS;

@Component
public class UserTOSValidator implements Validator {

  @Override
  public ValidationResult validate(User data) {
    ValidationResult result = new ValidationResult();
    if (Boolean.TRUE.equals(data.getAgreeToTerms())) {
      return result.setResult(true);
    }
    return result.setResult(false).setMessage(USER_TOS);
  }
}
