package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    List<SectorDto> toDtos(List<Sector> sectors);
}
