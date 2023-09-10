package demo.controller;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.mapper.UserMapper;
import demo.model.User;
import demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get paginated list of all created users")
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false) Integer pageSize
    ) {
        Page<User> users = userService.findAll(sortBy, pageNumber, pageSize);
        Page<UserDto> dto = users.map(userMapper::toDto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody SaveUserDto dto) {
        log.info("REST request to save user " + dto);
        User createdUser = userService.save(userMapper.toEntity(dto), dto.getSectorNames());
        return created(URI.create("/api/users/%s"
                .formatted(createdUser.getId())))
                .body(userMapper.toDto(createdUser));
    }
}
