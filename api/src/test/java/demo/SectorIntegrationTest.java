package demo;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class SectorIntegrationTest extends ContextIntegrationTest {
    @Test
    void findAll_shouldReturnAllSectors() throws Exception {
        createSector("A", null);

        mockMvc.perform(get("/sectors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[0].children").isEmpty());
    }
}