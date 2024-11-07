package demo.service;

import demo.model.Sector;

import java.util.List;

public interface ISectorService {

    Sector save(Sector sector);

    List<Sector> findAllByRootParent();

    Sector save(Long parentId, String name, int value);

    Sector findById(Long id);

    void deleteById(Long id);

    Sector update(Sector entity, Long sectorId);

    List<Sector> findByNames(List<String> names);

    List<Sector> findByIds(List<Long> ids);
}
