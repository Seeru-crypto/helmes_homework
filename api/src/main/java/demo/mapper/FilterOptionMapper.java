package demo.mapper;

import demo.controller.dto.FilterOptionDto;
import demo.controller.dto.FilterOptions;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilterOptionMapper {
  FilterOptionDto toDto(FilterOptions entity);

  List<FilterOptionDto> toDto(List<FilterOptions> entity);
}
