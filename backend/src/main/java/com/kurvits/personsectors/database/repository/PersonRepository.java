package com.kurvits.personsectors.database.repository;

import com.kurvits.personsectors.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Transactional
    PersonEntity findByName(String name);
}
