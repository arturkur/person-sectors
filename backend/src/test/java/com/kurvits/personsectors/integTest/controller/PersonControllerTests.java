package com.kurvits.personsectors.integTest.controller;

import com.kurvits.personsectors.controller.PersonController;
import com.kurvits.personsectors.database.entity.PersonEntity;
import com.kurvits.personsectors.database.repository.PersonRepository;
import com.kurvits.personsectors.database.repository.SectorRepository;
import com.kurvits.personsectors.integTest.BasePersonSectorsAppTest;
import com.kurvits.personsectors.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class PersonControllerTests extends BasePersonSectorsAppTest {

    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testUpdatePerson() throws Exception {
        UUID sectorId1 = sectorRepository.findByName("Kitchen").getId();
        UUID sectorId2 = sectorRepository.findByName("Metalworking").getId();
        Person person = new Person("test_name_1", true,
                List.of(sectorId1, sectorId2));

        testPersonPost(person);

        person.setSelectedSectors(List.of(sectorId1));

        testPersonPost(person);
    }

    @Test
    void testUpdatePersonValidationError() throws Exception {
        Person person = new Person("", true, List.of(UUID.randomUUID()));

        ResultActions resultActions = mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());

        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    void testUpdatePersonInvalidSectors() throws Exception {
        Person person = new Person("test_name", true, List.of(UUID.randomUUID()));

        ResultActions resultActions = mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isInternalServerError());

        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(resultString).isNotBlank();
    }

    @Test
    void testFindPersonByName() throws Exception {
        UUID sectorId1 = sectorRepository.findByName("Kitchen").getId();
        UUID sectorId2 = sectorRepository.findByName("Metalworking").getId();
        Person person = new Person("test_name_1", true,
                List.of(sectorId1, sectorId2));
        testPersonPost(person);

        Person response = testPersonGet("test_name_1");
        assertThat(response).isEqualTo(person);
    }

    private Person testPersonGet(String name) throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/person")
                .queryParam("name", name)).andExpect(status().isOk());

        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(resultString, Person.class);
    }

    private void testPersonPost(Person person) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        objectMapper.readValue(resultString, Person.class);

        PersonEntity savedPerson = personRepository.findByName(person.getName());

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getSelectedSectors()).hasSize(person.getSelectedSectors().size());
    }
}
