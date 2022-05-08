package com.kurvits.personsectors.database.repository;

import com.kurvits.personsectors.database.entity.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<SectorEntity, Long> {
    @Transactional
    Set<SectorEntity> findAllByParentNullOrderByNameAsc();

    @Transactional
    Set<SectorEntity> findAllByIdIn(List<UUID> ids);

    @Transactional
    SectorEntity findByName(String name);
}
