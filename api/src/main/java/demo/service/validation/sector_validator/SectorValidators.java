package demo.service.validation.sector_validator;

import demo.exception.BusinessException;
import demo.model.Sector;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationResult;
import org.springframework.stereotype.Component;

import static demo.service.validation.sector_validator.SectorErrors.*;

@Component
public class SectorValidators implements SectorValidator {
  private final SectorRepository sectorRepository;

  public SectorValidators(SectorRepository sectorRepository) {
    this.sectorRepository = sectorRepository;
  }

  @Override
  public ValidationResult validate(Sector sector) {
    ValidationResult result = new ValidationResult();

    if (sectorRepository.existsByName(sector.getName())) {
      return result.setValid(false).setMessage(NAME_EXISTS);
      }

    if (sectorRepository.existsByValue(sector.getValue())) {
      return result.setValid(false).setMessage(NAME_EXISTS);
    }

    return result.setValid(true);
  }
}
