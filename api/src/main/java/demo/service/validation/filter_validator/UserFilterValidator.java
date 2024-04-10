package demo.service.validation.filter_validator;

import demo.model.Filter;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.filter_validator.FilterErrors.FILTER_ERROR;

@Component
public class UserFilterValidator implements Validator<Filter> {

  public UserFilterValidator() {
  }

  @Override
  public ValidationResult validate(Filter filter) {
    ValidationResult result = new ValidationResult();

    // Check if the sector name length is within the allowed range

    if (filter.getCriteria() == "tere") {
      return result.setValid(false).setMessage(FILTER_ERROR);
    }

    return result.setValid(true);
  }
}
