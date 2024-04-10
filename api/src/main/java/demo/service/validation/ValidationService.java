package demo.service.validation;

import demo.controller.dto.FilterDto;
import demo.exception.BusinessException;
import demo.model.Filter;
import demo.model.Sector;
import demo.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class ValidationService {
  private final List<Validator<User>> userValidator;
  private final List<Validator<Filter>> filterValidator;
  private final List<Validator<FilterDto>> filterDtoValidator;
  private final List<Validator<Sector>> sectorValidators;
  private final List<Validator<Pageable>> pageableValidator;

  public <T> void validateEntity(T entity, List<Validator<T>> validators) {
    ValidationResult validationResult = validators.stream()
            .map(validator -> validator.validate(entity))
            .filter(result -> !result.isValid())
            .findFirst()
            .orElse(new ValidationResult().setValid(true)); // If no validation failure, return a successful result

    validationCleanup(validationResult);
  }

  private void validationCleanup(ValidationResult validationResult) {
    if (validationResult.isValid()) return;

    log.warn("data validation failed: {}", validationResult.getMessage());
    throw new BusinessException("DEFAULT_ERROR") {
      // Override getMessage() to provide a custom error message
      @Override
      public String getMessage() {
        return validationResult.getMessage().getKood();
      }
    };
  }
}
