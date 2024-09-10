package demo.mapper;

import demo.controller.dto.FilterDto;
import demo.model.Filter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilterMapper {

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  Filter toEntity (FilterDto dto);

  FilterDto toDto(Filter entity);

}
