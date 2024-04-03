package demo.service;

import demo.model.Sector;
import demo.repository.SectorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
  public Sector save(Long parentId, String name, int value) {
    return sectorRepository.save(new Sector()
            .setName(name)
            .setValue(value)
            .setParentId(parentId)
            .setChildren(new ArrayList<>())
    );
  }
}
