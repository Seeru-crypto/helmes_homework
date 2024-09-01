package demo.controller;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.mapper.UserMapper;
import demo.model.User;
import demo.model.UserFilter;
import demo.service.FilterService;
import demo.service.SectorService;
import demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
  private final UserService userService;
  private final SectorService sectorService;
  private final UserMapper userMapper;
  private final FilterService filterService;

  @GetMapping
  @Operation(summary = "Get paginated list of all created users")
  public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
    log.info("REST to find all users: " + pageable);
    Page<User> users = userService.findAll(pageable);
    Page<UserDto> dto = users.map(userMapper::toDto);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  @Operation(summary = "save new user")
  public ResponseEntity<UserDto> save(@Valid @RequestBody SaveUserDto dto) {
    log.info("REST request to save user: " + dto);
    User tempUser = userMapper.toEntity(dto);
    User createdUser = userService.save(tempUser);
    return created(URI.create("/api/users/%s"
            .formatted(createdUser.getId())))
            .body(userMapper.toDto(createdUser));
  }

  @PutMapping
  @Operation(summary = "Update user")
  public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto dto) {
    log.info("REST request to update user: " + dto);
    User updatedUser = userService.update(userMapper.toEntity(dto));
    return ResponseEntity.ok(userMapper.toDto(updatedUser));
  }

  @DeleteMapping(path = "/{userId}")
  @Operation(summary = "Delete an existing user by user id")
  public void delete(@PathVariable UUID userId) {
    log.info("REST request to delete user: " + userId);
    userService.delete(userId);
  }

  @GetMapping(path="sector/{sectorId}")
  @Operation(summary = "Get all users by sector id")
  public ResponseEntity<List<UserDto>> findAllBySector(@PathVariable Long sectorId) {
    log.info("REST request to get all users by sector: " + sectorId);
    List<User> users = userService.findAllBySector(sectorId);
    List<UserDto> dto = users.stream().map(userMapper::toDto).toList();
    return ResponseEntity.ok(dto);
  }
}
