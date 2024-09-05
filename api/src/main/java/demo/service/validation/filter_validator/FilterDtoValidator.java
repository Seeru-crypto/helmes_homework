package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.*;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static demo.service.validation.filter_validator.FilterErrors.*;

@Component
public class FilterDtoValidator implements Validator<FilterDto> {

  public FilterDtoValidator() {
  }

  @Override
  public ValidationResult validate(FilterDto filterDto) {
    ValidationResult result = new ValidationResult();
    // Check if the sector name length is within the allowed range

    switch (filterDto.getType()) {
      case DATE -> {
        return dateValidator(filterDto, result);
      }
      case NUMBER ->  {
        return numberValidator(filterDto, result);
      }
      case STRING -> {
        return stringValidator(filterDto, result);
      }
      default -> {
        return result.setValid(true);
      }
    }
  }

  private ValidationResult numberValidator(FilterDto filterDto, ValidationResult result) {
    if (!Filters.isStringInEnumList(filterDto.getCriteriaValue(), NumberCriteria.class)){
      return result.setValid(false).setMessage(INVALID_CRITERIA_ERROR);
    }
    if (!isNumberValid(filterDto.getValue())) {
      return result.setValid(false).setMessage(INVALID_NUMBER_VALUE);
    }
    return result.setValid(true);
  }

  private ValidationResult stringValidator(FilterDto filterDto, ValidationResult result) {
    if (!Filters.isStringInEnumList(filterDto.getCriteriaValue(), StringCriteria.class)){
      return result.setValid(false).setMessage(INVALID_CRITERIA_ERROR);
    }
    return result.setValid(true);
  }

  private ValidationResult dateValidator(FilterDto filterDto, ValidationResult result) {
    if (!Filters.isStringInEnumList(filterDto.getCriteriaValue(), DateCriteria.class)){
      return result.setValid(false).setMessage(INVALID_CRITERIA_ERROR);
    }

    if (!isDateValid(filterDto.getValue())) {
      return result.setValid(false).setMessage(INVALID_DATE_VALUE);
    }
    return result.setValid(true);
  }

  private boolean isDateValid(String dateString) {
    try {
      Instant.parse(dateString);
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }

  private boolean isNumberValid(String numberString) {
    try {
      Integer.parseInt(numberString);
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }
}
