package demo.controller;

import demo.controller.dto.SectorDto;
import lombok.RequiredArgsConstructor;
import demo.mapper.SectorMapper;
import org.springframework.web.bind.annotation.*;
import demo.service.SectorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/sectors")
public class SectorController {
    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    @GetMapping
    public List<SectorDto> findAll() {
        return sectorMapper.toDtos(sectorService.findAll());
    }

    @GetMapping("/{id}")
    public SectorDto findById(@PathVariable Long id) {
        return sectorMapper.toDto(sectorService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        sectorService.deleteById(id);
    }
}
