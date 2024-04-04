package demo.repository;

import demo.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findAllByParentId(Long parentId);

    List<Sector> findAllById(Long id);

    // võiks kasutada JQUERIT
    Sector findByName (String name);
}
