package com.kurvits.personsectors.controller;

import com.kurvits.personsectors.model.Person;
import com.kurvits.personsectors.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:34200"})
@Component
@RestController
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping("/api/person")
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) {
        LOG.info("Saving data for person: " + person.getName());
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    @GetMapping("/api/person")
    public ResponseEntity<Person> getPersonByName(@RequestParam String name) {
        LOG.info("Receiving user '{}' data", name);
        return ResponseEntity.ok(personService.findPersonByName(name));
    }
}
