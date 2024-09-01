package demo.service.validation.filter_validator;

import demo.controller.dto.UserFilterDto;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.filter_validator.FilterErrors.MAX_FILTER_LENGTH_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.MIN_FILTER_LENGTH_ERROR;

@Component
public class UserFilterDtoValidator implements Validator<UserFilterDto> {

  @Override
  public ValidationResult validate(UserFilterDto filter) {
    ValidationResult result = new ValidationResult();

    // Check if the sector name length is within the allowed range
    if (filter.getFilters().isEmpty()) {
      return result.setValid(false).setMessage(MIN_FILTER_LENGTH_ERROR);
    }

    else if (filter.getFilters().size() >= 5) {
      return result.setValid(false).setMessage(MAX_FILTER_LENGTH_ERROR);
    }
    return result.setValid(true);
  }
}
