package demo.repository;

import demo.model.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorJPARepository extends JpaRepository<SectorEntity, Long> {

    List<SectorEntity> findAllByParentId(Long parentId);

    List<SectorEntity> findAllByNameIn(List<String> names);

    // võiks kasutada JQUERIT
    SectorEntity findByName (String name);

    boolean existsByName(String name);

    boolean existsByValue(int value);
}
