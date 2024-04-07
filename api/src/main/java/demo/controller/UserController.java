package demo.controller;

import demo.controller.dto.SaveUserDto;
import demo.controller.dto.UserDto;
import demo.mapper.UserMapper;
import demo.model.Sector;
import demo.model.User;
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

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
  private final UserService userService;
  private final SectorService sectorService;
  private final UserMapper userMapper;

  @GetMapping
  @Operation(summary = "Get paginated list of all created users")
  public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
    Page<User> users = userService.findAll(pageable);
    Page<UserDto> dto = users.map(userMapper::toDto);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<UserDto> save(@Valid @RequestBody SaveUserDto dto) {
    log.info("REST request to save user " + dto);
    User tempUser = userMapper.toEntity(dto);
    User createdUser = userService.save(tempUser);
    return created(URI.create("/api/users/%s"
            .formatted(createdUser.getId())))
            .body(userMapper.toDto(createdUser));
  }

  @PutMapping(path = "/{userId}")
  public ResponseEntity<UserDto> update(@Valid @RequestBody SaveUserDto dto, @PathVariable Long userId) {
    log.info("REST request to update user " + dto);
    User updatedUser = userService.update(userMapper.toEntity(dto), userId);
    return ResponseEntity.ok(userMapper.toDto(updatedUser));
  }

  @DeleteMapping(path = "/{userId}")
  @Operation(summary = "Delete an existing user")
  public void delete(@PathVariable Long userId) {
    log.info("REST request to delete user " + userId);
    userService.delete(userId);
  }

  @GetMapping(path="sector/{sectorId}")
  public ResponseEntity<List<UserDto>> findAllBySector(@PathVariable Long sectorId) {
    log.info("REST request to get all users by sector " + sectorId);
    Sector existingSector = sectorService.findById(sectorId);
    List<User> users = userService.findAllBySector(existingSector);
    List<UserDto> dto = users.stream().map(userMapper::toDto).toList();
    return ResponseEntity.ok(dto);
  }
}
