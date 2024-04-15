package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
import demo.service.filter.StringCriteria;
import demo.service.validation.ValidationErrors;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static demo.service.validation.filter_validator.FilterErrors.*;

@Slf4j
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
        // apply date validations
        return dateValidator(filterDto, result);
      }
      case NUMBER ->  {
        // apply numeric validations
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
    if (!NumberCriteria.isStringInEnumList(filterDto.getCriteria())){
      logWarning(INVALID_CRITERIA_ERROR, filterDto.getCriteria());
      return result.setValid(false).setMessage(INVALID_CRITERIA_ERROR);
    }
    if (!isNumberValid(filterDto.getValue())) {
      logWarning(INVALID_NUMBER_VALUE, filterDto.getValue());
      return result.setValid(false).setMessage(INVALID_NUMBER_VALUE);
    }
    return result.setValid(true);
  }
  private ValidationResult stringValidator(FilterDto filterDto, ValidationResult result) {
    if (!StringCriteria.isStringInEnumList(filterDto.getCriteria())){
      logWarning(INVALID_CRITERIA_ERROR, filterDto.getCriteria());
      return result.setValid(false).setMessage(INVALID_CRITERIA_ERROR);
    }
    return result.setValid(true);
  }

  private ValidationResult dateValidator(FilterDto filterDto, ValidationResult result) {
    if (!DateCriteria.isStringInEnumList(filterDto.getCriteria())){
      logWarning(INVALID_CRITERIA_ERROR, filterDto.getCriteria());
      return result.setValid(false).setMessage(INVALID_CRITERIA_ERROR);
    }

    if (!isDateValid(filterDto.getValue())) {
      logWarning(INVALID_DATE_VALUE, filterDto.getValue());
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

  private void logWarning(ValidationErrors errors, String value) {
    log.warn("{}: {}",errors, value);
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
