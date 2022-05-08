package com.kurvits.personsectors.controller;

import com.kurvits.personsectors.model.Sector;
import com.kurvits.personsectors.service.SectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:34200"})
@Component
@RestController
public class SectorController {

    @Autowired
    private SectorService sectorService;

    private static final Logger LOG = LoggerFactory.getLogger(SectorController.class);

    @GetMapping("/api/sectors")
    public ResponseEntity<List<Sector>> getAllSectors() {
        LOG.info("Retrieving all sectors");
        return ResponseEntity.ok(sectorService.getAllSectors());
    }
}
