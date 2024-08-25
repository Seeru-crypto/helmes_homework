package demo.repository;

import demo.model.SectorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SectorRepository {

    private final SectorJPARepository sectorJPARepository;

    public List<SectorEntity> findAllByParentId(Long parentId) {
        var initialRes = sectorJPARepository.findAllByParentId(parentId);

        //  MAP things with Mapstruct or smth other
        var mappedResult = initialRes;


        return mappedResult;
    }
//
//        List<Sector> findAllByNameIn(List<String> names);
//
//        // võiks kasutada JQUERIT
//        Sector findByName (String name);
//
//        boolean existsByName(String name);
//
//        boolean existsByValue(int value);
//
}
