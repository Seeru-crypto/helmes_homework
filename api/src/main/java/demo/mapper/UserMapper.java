package demo.mapper;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(SaveUserDto saveUserDto);
}
