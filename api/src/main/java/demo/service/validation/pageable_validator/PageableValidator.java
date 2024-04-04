package demo.service.validation.pageable_validator;

import demo.model.PageableProps;
import demo.service.validation.ValidationResult;

public interface PageableValidator {
  ValidationResult validate(PageableProps pageableProps);
}
