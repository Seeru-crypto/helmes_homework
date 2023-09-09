package mapper;

import controller.dto.SectorDto;
import model.Sector;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    SectorDto toDto(Sector sector);

    List<SectorDto> toDtos(Collection<Sector> sectors);

    List<Sector> toEntities(Collection<SectorDto> sectorDtos);
}
