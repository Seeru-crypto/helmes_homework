package demo.mapper;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.model.Sector;
import demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilterMapper {
}
