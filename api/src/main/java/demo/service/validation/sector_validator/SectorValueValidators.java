package demo.service.validation.sector_validator;

import demo.model.SectorEntity;
import demo.repository.SectorJPARepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.sector_validator.SectorErrors.NAME_EXISTS;

@Component
public class SectorValueValidators implements Validator<SectorEntity> {
  private final SectorJPARepository sectorRepository;

  public SectorValueValidators(SectorJPARepository sectorRepository) {
    this.sectorRepository = sectorRepository;
  }

  @Override
  public ValidationResult validate(SectorEntity sector) {
    ValidationResult result = new ValidationResult();

    if (sectorRepository.existsByValue(sector.getValue())) {
      return result.setValid(false).setMessage(NAME_EXISTS);
    }

    return result.setValid(true);
  }
}
