package demo.mapper;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import demo.service.SectorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = { SectorMapper.class, SectorService.class})
public interface UserMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "sectors", source = "sectorIds")
    User toEntity(SaveUserDto saveUserDto);

    @Mapping(target  = "sectorNames", source = "sectors", qualifiedByName = "mapSectorsToNames")
    UserDto toDto(User user);

    @Named("mapSectorsToNames")
    default List<String> getSectorNames(List<Sector> sectors) {
        if (CollectionUtils.isEmpty(sectors)) {
            return new ArrayList<>();
        }

        return sectors.stream().map(Sector::getName).toList();
    }

    @Mapping(target = "sectors", source = "sectorNames")
    User toEntity(UserDto userDto);
}
