package demo.mapper;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.UserFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SectorMapper.class, UserMapper.class})
public interface FilterMapper {

  Filter toEntity (FilterDto dto);

  FilterDto toDto(Filter entity);

}
