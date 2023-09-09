package demo.controller;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.SectorDto;
import demo.controller.dto.UserDto;
import demo.mapper.UserMapper;
import demo.model.User;
import demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping
    public List<UserDto> findAll() {
        return userMapper.toDtos(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody SaveUserDto dto){
        log.info("REST request to save user " + dto);
        User createdUser = userService.save(dto);
        return created(URI.create("/api/users/%s"
                .formatted(createdUser.getId())))
                .body(userMapper.toDto(createdUser));

    }

}
