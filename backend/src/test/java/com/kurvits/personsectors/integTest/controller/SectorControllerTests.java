package com.kurvits.personsectors.integTest.controller;

import com.kurvits.personsectors.controller.SectorController;
import com.kurvits.personsectors.database.repository.SectorRepository;
import com.kurvits.personsectors.integTest.BasePersonSectorsAppTest;
import com.kurvits.personsectors.model.Sector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SectorControllerTests extends BasePersonSectorsAppTest {

    @Autowired
    SectorController sectorController;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllSectors() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/sectors")).andExpect(status().isOk());

        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        List<Sector> sectors = Arrays.asList(objectMapper.readValue(resultString, Sector[].class));

        assertThat(sectors).isNotEmpty();

        List<Sector> flatList = new ArrayList<>();
        for (Sector sector : sectors) {
            flattenSectors(sector, flatList);
        }

        assertThat(flatList.size()).isEqualTo(sectorRepository.count());
    }

    private void flattenSectors(Sector sector, List<Sector> flatList) {
        flatList.add(sector);
        if (sector.getChildren() != null && !sector.getChildren().isEmpty()) {
            for (Sector s : sector.getChildren()) {
                flattenSectors(s, flatList);
            }
        }
    }
}
