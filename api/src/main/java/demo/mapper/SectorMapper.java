package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.model.SectorEntity;
import demo.service.SectorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { SectorService.class})
public interface SectorMapper {
    List<SectorDto> toDtos(List<SectorEntity> sectors);

    List<SectorEntity> toEntity(List<SectorDto> dtos);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    SectorEntity toEntity (SectorDto sectorDto);

    SectorDto toDto (SectorEntity sector);

    List<SectorEntity> toEntityList(List<Long> sectorIds);

    List<SectorEntity> mapToSector(List<String> names);

    List<String> mapToName(List<SectorEntity> sectors);
}
