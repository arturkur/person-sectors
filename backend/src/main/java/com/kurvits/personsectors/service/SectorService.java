package com.kurvits.personsectors.service;

import com.kurvits.personsectors.database.entity.SectorEntity;
import com.kurvits.personsectors.database.repository.SectorRepository;
import com.kurvits.personsectors.model.Sector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class SectorService {

    private static final Logger LOG = LoggerFactory.getLogger(SectorService.class);

    @Autowired
    private SectorRepository sectorRepository;

    public List<Sector> getAllSectors() {
        Set<SectorEntity> sectorEntities = sectorRepository.findAllByParentNullOrderByNameAsc();
        List<Sector> sectors = new ArrayList<>();
        for (SectorEntity sectorEntity : sectorEntities) {
            sectors.add(mapChildren(sectorEntity));
        }
        return sectors;
    }

    public Set<SectorEntity> getSectorEntitiesByIdList(List<UUID> ids) {
        Set<SectorEntity> sectorEntities = sectorRepository.findAllByIdIn(ids);
        LOG.info("Found {} sectors with given ids", sectorEntities.size());
        return sectorEntities;
    }

    private Sector mapChildren(SectorEntity sectorEntity) {
        Sector sector = new Sector();
        sector.setId(sectorEntity.getId());
        sector.setName(sectorEntity.getName());
        if (sectorEntity.getChildren() != null && !sectorEntity.getChildren().isEmpty()) {
            for (SectorEntity child : sectorEntity.getChildren()) {
                if (sector.getChildren() == null) {
                    sector.setChildren(new ArrayList<>());
                }
                sector.getChildren().add(mapChildren(child));
            }
        }
        return sector;
    }
}
