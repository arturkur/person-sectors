package com.kurvits.personsectors.controller;

import com.kurvits.personsectors.model.Sector;
import com.kurvits.personsectors.service.SectorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SectorControllerTests {

    @Mock
    SectorService sectorService;

    @InjectMocks
    SectorController sectorController;

    @Test
    void testGetAllSectors() {
        List<Sector> sectors = List.of(new Sector(UUID.randomUUID(), "Service", null));

        when(sectorService.getAllSectors()).thenReturn(sectors);

        ResponseEntity<List<Sector>> response = sectorController.getAllSectors();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(sectors);
    }
}
