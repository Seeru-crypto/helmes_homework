package demo.service;

import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.repository.SectorRepository;
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
    private final SectorRepository sectorRepository;
    private final ValidationService validationService;

    public Sector save(Sector sector) {
        validationService.validateEntity(sector, validationService.getSectorValidators());
        return sectorRepository.save(sector);
    }

    @Transactional
    public List<Sector> findAllByParent() {
        return sectorRepository.findAllByParentId(null);
    }

    @Transactional
    public List<Sector> findAll() {
        return sectorRepository.findAll();
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

    public Sector findByName(String name) {
        return sectorRepository.findByName(name);
    }

    public void deleteById(Long id) {
        Sector sector = findById(id);
        // update existing parent-child connections
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

    public String getSectorName(Sector value) {
        return value.getName();
    }

    public List<Sector> findByNames(List<String> names) {
        return sectorRepository.findAllByNameIn(names);
    }
}
