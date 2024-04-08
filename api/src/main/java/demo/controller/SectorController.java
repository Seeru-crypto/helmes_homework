package demo.controller;

import demo.controller.dto.SectorDto;
import demo.mapper.SectorMapper;
import demo.model.Sector;
import demo.service.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/sectors")
public class SectorController {
    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    @GetMapping
    public List<SectorDto> findAll() {
        log.info("REST request to findAll sectors");
        return sectorMapper.toDtos(sectorService.findAll());
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<SectorDto> save(@Valid @RequestBody SectorDto sectorDto) {
        log.info("REST request to save sector: {}", sectorDto);
        Sector createdSector = sectorService.save(sectorMapper.toEntity(sectorDto));
        return ResponseEntity.ok(sectorMapper.toDto(createdSector));
    }

    @PutMapping
    public ResponseEntity<SectorDto> update(@Valid @RequestBody SectorDto dto) {
        log.info("REST request to update sector: " + dto);
        Sector updatedSector = sectorService.update(sectorMapper.toEntity(dto));
        return ResponseEntity.ok(sectorMapper.toDto(updatedSector));
        }
}
