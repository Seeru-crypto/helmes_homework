package demo.controller;


import demo.controller.dto.UserFilterDto;
import demo.mapper.FilterMapper;
import demo.service.FilterService;
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


  @PostMapping
  @Operation(summary = "Get user filters")
  public void saveFilter(@RequestBody UserFilterDto filterDto) {
  }









}
