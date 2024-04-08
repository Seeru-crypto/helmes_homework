package demo.mapper;

import demo.controller.dto.UserFilterDto;
import demo.model.UserFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SectorMapper.class, UserMapper.class, FilterMapper.class})
public interface UserFilterMapper {
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedBy", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  UserFilter toEntity (UserFilterDto filterDto);


  UserFilterDto toDto(UserFilter userFilterDto);

}
