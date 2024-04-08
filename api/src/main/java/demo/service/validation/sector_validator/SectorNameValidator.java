package demo.service.validation.sector_validator;

import demo.model.Sector;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.Validator;
import org.springframework.stereotype.Component;

import static demo.service.validation.sector_validator.SectorErrors.INCORRECT_LENGTH;
import static demo.service.validation.sector_validator.SectorErrors.NAME_EXISTS;

@Component
public class SectorNameValidator implements Validator<Sector> {

  public static final int SECTOR_MIN_NAME_LENGTH = 3;
  public static final int SECTOR_MAX_NAME_LENGTH = 50;
  private final SectorRepository sectorRepository;

  public SectorNameValidator(SectorRepository sectorRepository) {
    this.sectorRepository = sectorRepository;
  }

  @Override
  public ValidationResult validate(Sector sector) {
    ValidationResult result = new ValidationResult();

    // Check if the sector name already exists
    if (sectorRepository.existsByName(sector.getName())) {
      return result.setValid(false).setMessage(NAME_EXISTS);
      }

    // Check if the sector name length is within the allowed range
    if (sector.getName().length() < SECTOR_MIN_NAME_LENGTH || sector.getName().length() > SECTOR_MAX_NAME_LENGTH) {
      return result.setValid(false).setMessage(INCORRECT_LENGTH);
    }

    return result.setValid(true);
  }
}
