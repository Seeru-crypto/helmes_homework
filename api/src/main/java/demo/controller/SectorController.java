package demo.controller;

import demo.controller.dto.SectorDto;
import demo.mapper.SectorMapper;
import demo.model.Sector;
import demo.service.ISectorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/sectors")
public class SectorController {
    private final ISectorService sectorService;
    private final SectorMapper sectorMapper;

    @GetMapping
    @Operation(summary = "Find all sectors by root parentId")
    public List<SectorDto> findAllByParentId() {
        log.info("REST request to findAll sectors");
        return sectorMapper.toDtos(sectorService.findAllByRootParent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "get sector by id")
    public SectorDto findById(@PathVariable Long id) {
        log.info("REST request to sector by id: {}", id);
        return sectorMapper.toDto(sectorService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing sector by id, deletes all relationships as well")
    public void deleteById(@PathVariable Long id) {
        log.info("REST request to delete sector by id: {}", id);
        sectorService.deleteById(id);
    }

    @PostMapping
    @Operation(summary = "Save new sector")
    public ResponseEntity<SectorDto> save(@RequestBody SectorDto sectorDto) {
        log.info("REST request to save sector: {}", sectorDto);
        Sector createdSector = sectorService.save(sectorMapper.toEntity(sectorDto));
        return ResponseEntity.ok(sectorMapper.toDto(createdSector));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update existing sector")
    public ResponseEntity<SectorDto> update(@PathVariable Long id, @RequestBody SectorDto dto) {
        log.info("REST request to update sector with id {}: {} ", id, dto);
        Sector updatedSector = sectorService.update(sectorMapper.toEntity(dto), id);
        return ResponseEntity.ok(sectorMapper.toDto(updatedSector));
    }
}
