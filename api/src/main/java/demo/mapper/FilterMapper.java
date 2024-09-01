package demo.mapper;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Filter;
import demo.model.UserFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SectorMapper.class, UserMapper.class})
public interface FilterMapper {

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  Filter toEntity (FilterDto dto);

  FilterDto toDto(Filter entity);

}
