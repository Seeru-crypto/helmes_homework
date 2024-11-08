package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.service.ISectorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ISectorService.class})
public interface SectorMapper {

    List<SectorDto> toDtos(List<Sector> sectors);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Sector toEntity (SectorDto sectorDto);

    SectorDto toDto (Sector sector);
}
