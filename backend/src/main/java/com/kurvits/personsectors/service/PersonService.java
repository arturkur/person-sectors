package com.kurvits.personsectors.service;

import com.kurvits.personsectors.database.entity.PersonEntity;
import com.kurvits.personsectors.database.entity.SectorEntity;
import com.kurvits.personsectors.database.repository.PersonRepository;
import com.kurvits.personsectors.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SectorService sectorService;

    public Person updatePerson(Person person) {
        PersonEntity personEntity = personRepository.findByName(person.getName());
        if (personEntity == null) {
            personEntity = new PersonEntity();
            personEntity.setName(person.getName());
        }

        Set<SectorEntity> sectorEntities = sectorService.getSectorEntitiesByIdList(person.getSelectedSectors());
        if (sectorEntities == null || sectorEntities.isEmpty()) {
            throw new IllegalArgumentException("invalid sectors selected");
        }

        personEntity.setTermsAccepted(person.getTermsAccepted());
        personEntity.setSelectedSectors(sectorEntities);
        PersonEntity savedEntity = personRepository.save(personEntity);
        return mapPerson(savedEntity);
    }

    public Person findPersonByName(String name) {
        PersonEntity personEntity = personRepository.findByName(name);
        if (personEntity != null) {
            return mapPerson(personEntity);
        }
        return null;
    }

    private Person mapPerson(PersonEntity personEntity) {
        Person person = new Person();
        person.setName(personEntity.getName());
        person.setTermsAccepted(personEntity.getTermsAccepted());
        person.setSelectedSectors(new ArrayList<>());
        for (SectorEntity sectorEntity : personEntity.getSelectedSectors()) {
            person.getSelectedSectors().add(sectorEntity.getId());
        }
        return person;
    }
}
