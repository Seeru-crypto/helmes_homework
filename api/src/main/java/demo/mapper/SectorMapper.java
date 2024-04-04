package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.service.SectorService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { SectorService.class})
public interface SectorMapper {
    List<SectorDto> toDtos(List<Sector> sectors);

    List<Sector> toEntity(List<SectorDto> dtos);

    Sector toEntity (Long id);

    SectorDto toDto (Sector sector);
}
