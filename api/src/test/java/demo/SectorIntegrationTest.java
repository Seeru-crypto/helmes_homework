package demo;

import demo.model.Sector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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

  }

}