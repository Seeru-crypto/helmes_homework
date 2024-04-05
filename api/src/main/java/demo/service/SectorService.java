package demo.service;

import demo.exception.BusinessException;
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
    return sectorRepository.findById(id).orElse(null);
  }
}
