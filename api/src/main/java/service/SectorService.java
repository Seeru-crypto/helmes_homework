package service;

import jakarta.transaction.Transactional;
import model.Sector;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import repository.SectorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository sectorRepository;

    @Transactional
    public List<Sector> findAll() {
        return sectorRepository.findAllByParentId(null);
    }
}
