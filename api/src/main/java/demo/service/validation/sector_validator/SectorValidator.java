package demo.service.validation.sector_validator;

import demo.model.Sector;
import demo.service.validation.ValidationResult;

public interface SectorValidator {
  ValidationResult validate(Sector sector);
}
