package demo.controller;

import demo.controller.dto.FilterOptionsDto;
import demo.controller.dto.UserFilterDto;
import demo.mapper.UserFilterMapper;
import demo.model.UserFilter;
import demo.service.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/filters")
public class FilterController {
  private final FilterService filterService;
  private final UserFilterMapper userFilterMapper;

  @GetMapping
  @Operation(summary = "Get filter options")
  public ResponseEntity<List<FilterOptionsDto>> findAllOptions() {
    log.info("GET filter options request");
    return ResponseEntity.ok(filterService.findAllOptions());
  }

  @GetMapping(path = "/{userId}")
  @Operation(summary = "Get user saved filters")
  public ResponseEntity<List<UserFilterDto>> findAllByUser(@PathVariable UUID userId) {
    log.info("GET filters by user id: {}", userId);
    List<UserFilter> res =  filterService.findByUser(userId);
    return ResponseEntity.ok(userFilterMapper.toDto(res));
  }

  @PostMapping("/{userId}")
  @Operation(summary = "save a new filter to the specified user")
  public UserFilterDto saveFilter(@RequestBody UserFilterDto filterDto, @PathVariable UUID userId) {
    log.info("SAVE filter to user: {}, with filterDto: {}", userId, filterDto);
    var createdFilter = filterService.saveFilters(filterDto, userId);
    return userFilterMapper.toDto(createdFilter);
  }
}
