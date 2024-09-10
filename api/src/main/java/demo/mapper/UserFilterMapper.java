package demo.mapper;

import demo.controller.dto.UserFilterDto;
import demo.model.User;
import demo.model.UserFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFilterMapper {
  UserFilterDto toDto(UserFilter userFilterDto);

  @Mapping(target = "id", source = "userFilterDto.id")
  @Mapping(target = "name", source = "userFilterDto.name")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "filters", ignore = true)
  UserFilter toEntity(UserFilterDto userFilterDto, User user);

  List<UserFilterDto> toDto(List<UserFilter> userFilterDto);

}
