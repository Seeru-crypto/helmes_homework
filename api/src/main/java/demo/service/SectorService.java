package demo.service;

import demo.model.Sector;
import demo.repository.SectorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository sectorRepository;

    @Transactional
    public List<Sector> findAll() {
        return sectorRepository.findAllByParentId(null);
    }


    public Sector save(Long parentId, String name){
        return sectorRepository.save(new Sector()
                .setName(name)
                .setParentId(parentId)
                .setChildren(new ArrayList<>())
        );
    }
}
