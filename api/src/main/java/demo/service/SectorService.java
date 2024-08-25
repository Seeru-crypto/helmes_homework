package demo.service;

import demo.exception.NotFoundException;
import demo.model.SectorEntity;
import demo.repository.SectorJPARepository;
import demo.service.validation.ValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorJPARepository sectorRepository;
    private final ValidationService validationService;

    public SectorEntity save(SectorEntity sector) {
        validationService.validateEntity(sector, validationService.getSectorValidators());
        return sectorRepository.save(sector);
    }

    @Transactional
    public List<SectorEntity> findAllByParent() {
        return sectorRepository.findAllByParentId(null);
    }

    private void addChildren(SectorEntity child) {
        SectorEntity parent = findById(child.getParentId());
        parent.addChild(child);
    }

    @Transactional
    public SectorEntity save(Long parentId, String name, int value) {
        SectorEntity child = sectorRepository.save(new SectorEntity()
                .setName(name)
                .setValue(value)
                .setParentId(parentId)
        );
        if (parentId != null) addChildren(child);
        return child;
    }

    public SectorEntity findById(Long id) {
        return sectorRepository.findById(id).orElseThrow(() -> {
            log.warn("Sector not found: {}", id);
            return new NotFoundException("Sector not found") {
            };
        });
    }

    public SectorEntity findByName(String name) {
        return sectorRepository.findByName(name);
    }

    public void deleteById(Long id) {
        SectorEntity sector = findById(id);
        // update existing parent-child connections
        updateParentChildConnections(sector);
        // delete the sector
        sector.removeAllChildren();
        sectorRepository.deleteById(id);
    }

    private void updateParentChildConnections(SectorEntity sector) {
        if (sector.getChildren().isEmpty()) return;

        sector.getChildren().forEach(child -> {
            child.setParentId(sector.getParentId());
            sectorRepository.save(child);
        });
    }

    public SectorEntity update(SectorEntity entity) {
        // for now we only update the sector name
        // moving a child from parent_A to parent_B is not allowed
        return findById(entity.getId()).setName(entity.getName());
    }

    public String getSectorName(SectorEntity value) {
        return value.getName();
    }

    public List<SectorEntity> findByNames(List<String> names) {
        return sectorRepository.findAllByNameIn(names);
    }
}
