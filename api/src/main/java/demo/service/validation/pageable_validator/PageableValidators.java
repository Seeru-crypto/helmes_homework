package demo.service.validation.pageable_validator;

import demo.model.PageableProps;
import demo.service.validation.ValidationResult;
import org.springframework.stereotype.Component;

import static demo.service.validation.pageable_validator.PageableErrors.PAGE_NUMBER_INVALID;
import static demo.service.validation.pageable_validator.PageableErrors.SIZE_OF_PAGE_INVALID;

@Component
public class PageableValidators implements PageableValidator {
  private static final int SIZE_PAGE_MIN = 0;
  private static final int SIZE_PAGE_MAX = 100;
  private static final int PAGE_NR_MIN = 0;

  @Override
  public ValidationResult validate(PageableProps pageableProps) {

    ValidationResult result = new ValidationResult();

    if (pageableProps.getSizeOfPage() < SIZE_PAGE_MIN || pageableProps.getSizeOfPage() >= SIZE_PAGE_MAX  ) {
      return result.setValid(false).setMessage(SIZE_OF_PAGE_INVALID);
    }

    if (pageableProps.getPageNumber() < PAGE_NR_MIN ) {
      return result.setValid(false).setMessage(PAGE_NUMBER_INVALID);
    }

    return result.setValid(true);
  }
}
