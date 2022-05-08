package com.kurvits.personsectors.service;

import com.kurvits.personsectors.database.entity.SectorEntity;
import com.kurvits.personsectors.database.repository.SectorRepository;
import com.kurvits.personsectors.model.Sector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SectorServiceTests {

    @Mock
    SectorRepository sectorRepository;

    @InjectMocks
    SectorService sectorService;

    @Test
    void testGetAllSectors() {
        Set<SectorEntity> sectorEntities = getTestSectorEntities();

        when(sectorRepository.findAllByParentNullOrderByNameAsc()).thenReturn(sectorEntities);

        List<Sector> sectors = sectorService.getAllSectors();

        assertThat(sectors.get(0).getName()).isEqualTo("Service");
        assertThat(sectors.get(0).getChildren()).hasSize(1);
        assertThat(sectors.get(0).getChildren().get(0).getName()).isEqualTo("Service child");
    }

    @Test
    void testGetSectorEntitiesByIdList() {
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());

        when(sectorRepository.findAllByIdIn(ids)).thenReturn(Set.of(
                new SectorEntity(), new SectorEntity()
        ));

        Set<SectorEntity> sectorEntities = sectorService.getSectorEntitiesByIdList(ids);

        assertThat(sectorEntities).hasSize(2);
    }

    private Set<SectorEntity> getTestSectorEntities() {
        SectorEntity child = new SectorEntity();
        child.setId(UUID.randomUUID());
        child.setName("Service child");
        child.setChildren(Set.of());
        SectorEntity parent = new SectorEntity();
        parent.setId(UUID.randomUUID());
        parent.setName("Service");
        parent.setChildren(Set.of(child));
        return Set.of(parent);
    }
}
