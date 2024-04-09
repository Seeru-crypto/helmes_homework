package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

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

    return result.setValid(true);
  }
}
