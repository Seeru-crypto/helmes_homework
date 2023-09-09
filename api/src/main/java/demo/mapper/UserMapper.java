package demo.mapper;

import demo.controller.dto.SectorDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDto> toDtos(Collection<User> users);

    UserDto toDto(User user);
}
