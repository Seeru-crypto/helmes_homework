package demo.controller;


import demo.controller.dto.FilterOptionsDto;
import demo.controller.dto.UserFilterDto;
import demo.mapper.UserFilterMapper;
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
  @Operation(summary = "Get user filters")
  public ResponseEntity<List<UserFilterDto>> findAllByUser(@PathVariable UUID userId) {
  return null;
  }

  @PostMapping("/{userId}")
  @Operation(summary = "Get user filters")
  public UserFilterDto saveFilter(@RequestBody UserFilterDto filterDto, @PathVariable UUID userId) {
    var user = userService.findById(userId);
    var createdFilter =  filterService.saveFilters(filterDto, user);
    return userFilterMapper.toDto(createdFilter);
  }
}
