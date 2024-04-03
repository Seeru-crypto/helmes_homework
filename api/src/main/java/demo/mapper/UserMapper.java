package demo.mapper;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = { SectorMapper.class })
public interface UserMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "sectors", source = "sectorIds")
    User toEntity(SaveUserDto saveUserDto);

    @Mapping(target  = "sectorIds", source = "sectors", qualifiedByName = "mapSectors")
    UserDto toDto(User user);

    @Named("mapSectors")
    default List<Long> mapSectors(List<Sector> value) {
        return value.stream().map(Sector::getId).toList();
    }


}
