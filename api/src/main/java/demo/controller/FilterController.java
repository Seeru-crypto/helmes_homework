package demo.controller;

import demo.controller.dto.FilterOptionsDto;
import demo.controller.dto.UserFilterDto;
import demo.mapper.UserFilterMapper;
import demo.model.User;
import demo.model.UserFilter;
import demo.service.FilterService;
import demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/filters")
public class FilterController {
  private final FilterService filterService;
  private final UserService userService;
  private final UserFilterMapper userFilterMapper;

  @GetMapping
  @Operation(summary = "Get filter options")
  public ResponseEntity<List<FilterOptionsDto>> findAllOptions() {
    return ResponseEntity.ok(filterService.findAllOptions());
  }

  @GetMapping(path = "/{userId}")
  @Operation(summary = "Get user filter options")
  public ResponseEntity<List<UserFilterDto>> findAllByUser(@PathVariable UUID userId) {
    log.info("GET filters by user id: {}", userId);
    User user = userService.findById(userId);
    List<UserFilter> res =  filterService.findByUser(user);
    return ResponseEntity.ok(userFilterMapper.toDto(res));
  }

  @PostMapping("/{userId}")
  @Operation(summary = "save a new filter to he specified user")
  public UserFilterDto saveFilter(@RequestBody UserFilterDto filterDto, @PathVariable UUID userId) {
    var user = userService.findById(userId);
    var createdFilter = filterService.saveFilters(filterDto, user);
    return userFilterMapper.toDto(createdFilter);
  }
}
