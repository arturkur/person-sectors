package com.kurvits.personsectors.controller;

import com.kurvits.personsectors.model.Person;
import com.kurvits.personsectors.service.PersonService;
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

@ExtendWith({MockitoExtension.class})
class PersonControllerTests {

    @Mock
    PersonService personService;

    @InjectMocks
    PersonController personController;

    @Test
    void testUpdatePerson() {
        Person person = new Person("test_name", true, List.of(UUID.randomUUID()));

        when(personService.updatePerson(person)).thenReturn(person);

        ResponseEntity<Person> response = personController.updatePerson(person);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(person);
    }

    @Test
    void testGetPersonByName() {
        Person person = new Person("test_name", true, List.of(UUID.randomUUID()));

        when(personService.findPersonByName("test_name")).thenReturn(person);

        ResponseEntity<Person> response = personController.getPersonByName("test_name");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(person);
    }
}
