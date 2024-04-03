package demo.service;

import demo.exception.BusinessException;
import demo.model.Sector;
import demo.repository.SectorRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  public Sector findByName(String name) {
    return sectorRepository.findByName(name);
  }

  public Sector findById(Long id) {
    return sectorRepository.findById(id).orElseThrow(() -> {
      log.warn("sector findById exception: given sector does not exist {}", id);
      return new BusinessException("given user does not exist") {
      };
    });
  }


}
