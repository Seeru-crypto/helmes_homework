package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.service.SectorService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = { SectorService.class})
public interface SectorMapper {
    List<SectorDto> toDtos(List<Sector> sectors);

//    List<Sector> toEntity(List<SectorDto> dtos);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Sector toEntity (SectorDto sectorDto);

    SectorDto toDto (Sector sector);

    List<Sector> toEntityList(List<Long> sectorIds);

    List<Sector> mapToSector(List<String> names);

    List<String> mapToName(List<Sector> sectors);
}
