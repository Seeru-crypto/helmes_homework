package demo.mapper;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import demo.service.SectorService;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { SectorMapper.class})
public interface UserMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "sectors", source = "sectorIds")
    User toEntity(SaveUserDto saveUserDto);

    @Mapping(target  = "sectorNames", source = "sectors")
    UserDto toDto(User user);

    @Mapping(target = "sectors", source = "sectorNames")
    User toEntity(UserDto userDto);
}
