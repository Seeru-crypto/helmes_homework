package demo.service.mapper;

import demo.ContextIntegrationTest;
import demo.model.Sector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SectorMapperTest extends ContextIntegrationTest {

    @BeforeEach
    void setUp() {
        createFullSectorTree();
    }

    @Test
    void sectorMapper_shouldReturn_ListOfSectors() {
        String initialName = "Manufacturing";
        List<Sector> res = sectorMapper.mapToSector(List.of(initialName));

        assertEquals(1, res.size());
        assertEquals(initialName, res.get(0).getName());
        assertEquals(1, res.get(0).getValue());
    }

    @Test
    void sectorMapper_shouldReturn_ListOfSectorNames() {
        String firstName = "Manufacturing";
        String secondName = "Living room";
        String thirdName = "Plastics welding and processing";
        List<String> expectedNames = List.of(firstName, secondName, thirdName);

        Sector first = sectorService.findByName(firstName);
        Sector second = sectorService.findByName(secondName);
        Sector third = sectorService.findByName(thirdName);

        List<Sector> input = List.of(first, second, third);
        List<String> output =  sectorMapper.mapToName(input);
        assertTrue(output.containsAll(expectedNames));
    }
}
