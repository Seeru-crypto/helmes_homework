package demo.service;

import demo.exception.BusinessException;
import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.model.User;
import demo.repository.SectorRepository;
import demo.service.validation.ValidationResult;
import demo.service.validation.sector_validator.SectorValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository sectorRepository;

    private final UserService userService;
    private final List<SectorValidator> sectorValidators;

    public Sector save(Sector sector) {
        validateSector(sector);

        return sectorRepository.save(sector);
    }

    protected void validateSector(Sector sector) {
        ValidationResult validationResult = sectorValidators.stream()
                .map(userValidator -> userValidator.validate(sector))
                .filter(result -> !result.isValid())
                .findFirst()
                .orElse(new ValidationResult().setValid(true)); // If no validation failure, return a successful result

        validationCleanup(validationResult);
    }

    private void validationCleanup(ValidationResult validationResult) {
        if (!validationResult.isValid()) {
            log.warn("sector validation failed: {}", validationResult.getMessage());
            throw new BusinessException("DEFAULT_ERROR") {
                // Override getMessage() to provide a custom error message
                @Override
                public String getMessage() {
                    return validationResult.getMessage().getKood();
                }
            };
        }
    }

    @Transactional
    public List<Sector> findAll() {
        return sectorRepository.findAllByParentId(null);
    }
    private void addChildren(Sector child) {
        Sector parent = findById(child.getParentId());
        parent.addChild(child);
    }

    @Transactional
    public Sector save(Long parentId, String name, int value) {
        Sector child = sectorRepository.save(new Sector()
                .setName(name)
                .setValue(value)
                .setParentId(parentId)
        );
        if (parentId != null) addChildren(child);
        return child;
    }

    public Sector findById(Long id) {
        return sectorRepository.findById(id).orElseThrow(() -> {
            log.warn("Sector not found: {}", id);
            return new NotFoundException("Sector not found") {
            };
        });
    }

    public void deleteById(Long id) {
        Sector sector = findById(id);
        // update existing parent-child connections

        // remove from user
        userService.removeSectorFromAllUsers(sector);

        updateParentChildConnections(sector);
        // delete the sector
        sector.removeAllChildren();
        sectorRepository.deleteById(id);
    }

    private void updateParentChildConnections(Sector sector) {
        if (sector.getChildren().isEmpty()) return;

        sector.getChildren().forEach(child -> {
            child.setParentId(sector.getParentId());
            sectorRepository.save(child);
        });
    }

    public Sector update(Sector entity) {
        // for now we only update the sector name
        // moving a child from parent_A to parent_B is not allowed
        return findById(entity.getId()).setName(entity.getName());
    }
}
