package demo.mapper;

import demo.controller.dto.UserFilterDto;
import demo.model.UserFilter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFilterMapper {
  UserFilterDto toDto(UserFilter userFilterDto);

  List<UserFilterDto> toDto(List<UserFilter> userFilterDto);

}
