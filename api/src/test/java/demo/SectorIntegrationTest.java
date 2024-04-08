package demo;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static demo.service.validation.sector_validator.SectorErrors.NAME_EXISTS;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SectorIntegrationTest extends ContextIntegrationTest {
    @Test
    void findAll_shouldReturnAllSectors() throws Exception {
        createFullSectorTree();

        mockMvc.perform(get("/sectors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Manufacturing"))
                .andExpect(jsonPath("$[0].children.length()").value(10));
    }

    @Test
    void findById_shouldReturnSingleSector() throws Exception {
        createFullSectorTree();

        mockMvc.perform(get("/sectors/{id}", 4L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Food and Beverage"))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.children.length()").value(6));
    }

    @Test
    void remove_shouldRemoveSector_onLastLeaf() throws Exception {
        createFullSectorTree();

        mockMvc.perform(delete("/sectors/{id}", 2L))
                .andDo(print())
                .andExpect(status().isOk())
        ;
        // TODO: replace entitymanager call with a sector service call
        assertFalse(sectorExists(2L));
    }

    @Test
    void remove_shouldRemoveSector_onTopLeaf() throws Exception {
        createFullSectorTree();

        List<Long> childIds = findSectorById(1L).getChildren().stream().map(Sector::getId).toList();
        assertEquals(10, childIds.size());

        mockMvc.perform(delete("/sectors/{id}", 1L))
                .andExpect(status().isOk())
        ;
        Assertions.assertFalse(sectorExists(1L));

        for (Long childId : childIds) {
            assertNull(findSectorById(childId).getParentId());
        }
    }

    @Test
    void remove_shouldRemoveSector_onMiddleLeaf() throws Exception {
        createFullSectorTree();

        Sector createdSector = findSectorById(4L);

        List<Sector> children = createdSector
                .getChildren();

        List<Long> childIds = children
                .stream()
                .map(Sector::getId)
                .toList();

        assertEquals(6, childIds.size());
        Long parentId = createdSector.getParentId();

        mockMvc.perform(delete("/sectors/{id}", createdSector.getId()))
                .andExpect(status().isOk())
        ;
        Assertions.assertFalse(sectorExists(4L));

        for (Long childId : childIds) {
            assertEquals(parentId, findSectorById(childId).getParentId());
        }
    }

    @Test
    void remove_shouldRemoveSector_fromUser_sectorTable() throws Exception {
        createFullSectorTree();
        List<Sector> list = List.of(findSectorById(1L), findSectorById(4L), findSectorById(6L));
        createUser("userq1", true, list);

        // assert user has 3 sectors connected
        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("sort", "id")
                        .param("size", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].sectors.length()").value(3))
                .andExpect(jsonPath("$.content[0].sectors[0]").value("Manufacturing"))
                .andExpect(jsonPath("$.content[0].sectors[1]").value("Food and Beverage"))
                .andExpect(jsonPath("$.content[0].sectors[2]").value("Beverages"))
        ;

        mockMvc.perform(delete("/sectors/{id}", 4L))
                .andExpect(status().isOk())
        ;

        // assert user has 3 sectors connected
        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("sort", "id")
                        .param("size", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].sectors.length()").value(2))
                .andExpect(jsonPath("$.content[0].sectors[0]").value("Manufacturing"))
                .andExpect(jsonPath("$.content[0].sectors[1]").value("Beverages"))
        ;
    }

    @Test
    void save_shouldSaveSector() throws Exception {
        SectorDto sectorDto = new SectorDto().setName("tere").setParentId(null).setValue(2);
        byte[] bytes = getBytes(sectorDto);
        mockMvc.perform(post("/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("tere"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.parentId").isEmpty())
                .andExpect(jsonPath("$.value").value(2))
                .andExpect(jsonPath("$.children.length()").value(0))
        ;
    }

    @Test
    void save_shouldReturnBadRequest_ifNameExists() throws Exception {
        Sector existingSetor = createSector("tere", null, 2);

        SectorDto sectorDto = new SectorDto().setName(existingSetor.getName()).setParentId(1L).setValue(23);
        byte[] bytes = getBytes(sectorDto);
        mockMvc.perform(post("/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(NAME_EXISTS.getKood()))
        ;
    }

    @Test
    void save_shouldReturnBadRequest_ifValueExists() throws Exception {
        Sector existingSetor = createSector("tere", null, 2);

        SectorDto sectorDto = new SectorDto().setName("pere").setParentId(1L).setValue(existingSetor.getValue());
        byte[] bytes = getBytes(sectorDto);
        mockMvc.perform(post("/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(NAME_EXISTS.getKood()))
        ;
    }

    @Test
    void update_shouldUpdateSector() throws Exception {
        Sector existingSetor = createSector("tere", null, 2);

        SectorDto sectorDto = new SectorDto().setName("pere").setParentId(null).setValue(2).setId(existingSetor.getId());
        byte[] bytes = getBytes(sectorDto);
        mockMvc.perform(put("/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bytes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("pere"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.children.length()").value(0))
        ;
    }
}