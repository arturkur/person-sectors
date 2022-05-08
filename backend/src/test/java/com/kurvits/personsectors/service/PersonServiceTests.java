package com.kurvits.personsectors.service;

import com.kurvits.personsectors.database.entity.PersonEntity;
import com.kurvits.personsectors.database.entity.SectorEntity;
import com.kurvits.personsectors.database.repository.PersonRepository;
import com.kurvits.personsectors.model.Person;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PersonServiceTests {

    @Mock
    PersonRepository personRepository;

    @Mock
    SectorService sectorService;

    @InjectMocks
    PersonService personService;

    @Test
    void testUpdatePersonNotExist() {
        Person person = new Person("test_name", true,
                List.of(UUID.fromString("ab4fe278-e4cc-4ce4-9a88-5dcf507c3a83")));

        when(personRepository.findByName(person.getName())).thenReturn(null);
        when(personRepository.save(any(PersonEntity.class))).thenReturn(getTestPersonEntity());
        when(sectorService.getSectorEntitiesByIdList(person.getSelectedSectors())).thenReturn(Set.of(getTestSectorEntity()));

        Person response = personService.updatePerson(person);

        assertThat(response).isEqualTo(person);
    }

    @Test
    void testUpdatePersonInvalidSectors() {
        Person person = new Person("test_name", true,
                List.of(UUID.randomUUID()));

        when(personRepository.findByName(person.getName())).thenReturn(getTestPersonEntity());
        when(sectorService.getSectorEntitiesByIdList(person.getSelectedSectors())).thenReturn(Set.of());

        assertThrows(IllegalArgumentException.class, () -> personService.updatePerson(person));
    }

    @Test
    void testFindPersonByName() {
        PersonEntity person = getTestPersonEntity();

        when(personRepository.findByName("test_name")).thenReturn(person);

        Person response = personService.findPersonByName("test_name");

        assertThat(response.getName()).isEqualTo(person.getName());
        assertThat(response.getTermsAccepted()).isEqualTo(person.getTermsAccepted());
    }

    @Test
    void testFindPersonByNameNull() {
        when(personRepository.findByName("test_name")).thenReturn(null);

        Person response = personService.findPersonByName("test_name");

        assertThat(response).isNull();
    }

    private PersonEntity getTestPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(UUID.fromString("b2d9c8bc-a74e-43bd-b4ab-e6e71de1bf87"));
        personEntity.setName("test_name");
        personEntity.setTermsAccepted(true);
        personEntity.setSelectedSectors(Set.of(getTestSectorEntity()));
        return personEntity;
    }

    private SectorEntity getTestSectorEntity() {
        SectorEntity sectorEntity = new SectorEntity();
        sectorEntity.setId(UUID.fromString("ab4fe278-e4cc-4ce4-9a88-5dcf507c3a83"));
        sectorEntity.setName("Service");
        sectorEntity.setChildren(null);
        return sectorEntity;
    }
}
