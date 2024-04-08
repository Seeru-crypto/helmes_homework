package demo.service.validation.pageable_validator;

import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static demo.service.validation.pageable_validator.PageableErrors.INVALID_SORT_BY_VALUE;
import static demo.service.validation.pageable_validator.PageableErrors.PAGE_SIZE_IS_TOO_LARGE;

@Component
public class PageableValidator implements Validator<Pageable> {
  private static final int MAX_PAGE_SIZE = 100;

  @Override
  public ValidationResult validate(Pageable entity) {
    ValidationResult result = new ValidationResult();
    entity.getPageSize();

    if (entity.getPageSize() > MAX_PAGE_SIZE) {
      return result.setValid(false).setMessage(PAGE_SIZE_IS_TOO_LARGE);
    }
    String sortBy = getSortBy(entity);

    if (!ValidSortByValues.isStringInEnumList(sortBy)) {
      return result.setValid(false).setMessage(INVALID_SORT_BY_VALUE);
    }

    return result.setValid(true);
  }

  // Method to get sortBy
  private static String getSortBy(Pageable pageable) {
    // Get Sort object from Pageable and extract sort properties
    Sort sort = pageable.getSort();
    return sort.stream()
            .map(order -> order.getProperty())
            .findFirst()
            .orElse("No sort by value provided");
  }
}
