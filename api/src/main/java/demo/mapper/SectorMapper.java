package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.service.SectorService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { SectorService.class})
public interface SectorMapper {
    List<SectorDto> toDtos(List<Sector> sectors);

    List<Sector> toEntity(List<SectorDto> dtos);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Sector toEntity (SectorDto sectorDto);

    SectorDto toDto (Sector sector);

    List<Sector> toEntityList(List<Long> sectorIds);

    List<Sector> mapToSector(List<String> sectorNames);

    List<String> mapToSectorNames(List<Sector> sectorNames);


}
