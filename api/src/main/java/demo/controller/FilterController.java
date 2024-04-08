package demo.controller;


import demo.controller.dto.UserFilterDto;
import demo.mapper.FilterMapper;
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
  private final FilterMapper filterMapper;

  @GetMapping
  @Operation(summary = "Get filter options")
  public void findAllOptions() {
  }

  @GetMapping(path = "/{userId}")
  @Operation(summary = "Get user filters")
  public ResponseEntity<List<UserFilterDto>> findAllByUser(@PathVariable UUID userId) {
  return null;
  }


  @PostMapping("/{userId}")
  @Operation(summary = "Get user filters")
  public UserFilter saveFilter(@RequestBody UserFilterDto filterDto, @PathVariable UUID userId) {
    var user = userService.findById(userId);
    return filterService.saveFilters(filterDto, user);
  }









}
