package demo;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

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
                .andDo(print())
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

    // TODO: Fix test
    @Test
    void remove_shouldRemoveSector() throws Exception {
        createFullSectorTree();

        mockMvc.perform(delete("/sectors/{id}", 1L))
                .andExpect(status().isOk());
    }
}