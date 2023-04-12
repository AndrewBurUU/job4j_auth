package ru.job4j.controller;

import org.apache.log4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;
import ru.job4j.service.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    /**private final PersonRepository persons;*/
    private final SpringPersonService persons;
    private static final Logger LOG = LogManager.getLogger(PersonController.class.getName());

    public PersonController(final SpringPersonService persons) {
        this.persons = persons;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<Person>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        try {
            this.persons.save(person);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        try {
            this.persons.delete(person);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ResponseEntity.badRequest().build();
    }
}
