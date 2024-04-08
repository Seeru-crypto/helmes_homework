package demo.service.validation.sector_validator;

import demo.model.Sector;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.sector_validator.SectorErrors.NAME_EXISTS;

@Component
public class SectorValueValidators implements Validator<Sector> {
  private final SectorRepository sectorRepository;

  public SectorValueValidators(SectorRepository sectorRepository) {
    this.sectorRepository = sectorRepository;
  }

  @Override
  public ValidationResult validate(Sector sector) {
    ValidationResult result = new ValidationResult();

    if (sectorRepository.existsByValue(sector.getValue())) {
      return result.setValid(false).setMessage(NAME_EXISTS);
    }

    return result.setValid(true);
  }
}
