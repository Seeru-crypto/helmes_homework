package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
import demo.service.filter.StringCriteria;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.filter_validator.FilterErrors.CRITERIA_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.DEFAULT_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.FILTER_ERROR;

@Component
public class FilterDtoValidator implements Validator<FilterDto> {

  public FilterDtoValidator() {
  }

  @Override
  public ValidationResult validate(FilterDto filterDto) {
    ValidationResult result = new ValidationResult();
    // Check if the sector name length is within the allowed range
    if (filterDto.getCriteria() == "tere") {
      return result.setValid(false).setMessage(FILTER_ERROR);
    }

    switch (filterDto.getType()) {
      case DATE -> {
        // apply date validations
        if (DateCriteria.isStringInEnumList(filterDto.getCriteria())){
          return result.setValid(false).setMessage(CRITERIA_ERROR);
        }

        // add value to Instant validation

      }
      case NUMBER ->  {
        // apply numeric validations
        if (NumberCriteria.isStringInEnumList(filterDto.getCriteria())){
          return result.setValid(false).setMessage(CRITERIA_ERROR);
        }

        // add value to Integer validation

      }
      case STRING -> {
        if (StringCriteria.isStringInEnumList(filterDto.getCriteria())){
          return result.setValid(false).setMessage(CRITERIA_ERROR);
        }
      }
      default -> {
        return result.setValid(false).setMessage(DEFAULT_ERROR);
      }
    }

    return result.setValid(true);
  }
}
