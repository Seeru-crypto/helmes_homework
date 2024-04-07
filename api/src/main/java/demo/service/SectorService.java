package demo.service;

import demo.exception.BusinessException;
import demo.exception.NotFoundException;
import demo.model.Sector;
import demo.repository.SectorRepository;
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

    @Transactional
    public List<Sector> findAll() {
        return sectorRepository.findAllByParentId(null);
    }

    // vüiks kasutada lombok builderit, kuna siis ei initisaliseeri

    private void addChildren(Sector child) {
        Sector parent = sectorRepository.findById(child.getParentId())
                .orElseThrow(() -> new BusinessException("Parent not found") {
                });
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
        sectorRepository.getReferenceById(2L);;

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

    public Sector save(Sector sector) {
        if (sectorRepository.existsByName(sector.getName())) {
            log.warn("Sector with the given name exists {}", sector.getName());
            throw new BusinessException("Sector with the given name exists") {
            };
        }

        if (sectorRepository.existsByValue(sector.getValue())) {
            log.warn("Sector with the given value  exists {}", sector.getValue());
            throw new BusinessException("Sector with the given value  exists") {
            };
        }

        return sectorRepository.save(sector);
    }

    public Sector update(Sector entity) {
        // for now we only update the sector name
        // moving a child from parent_A to parent_B is not allowed
        return findById(entity.getId()).setName(entity.getName());
    }
}
