package ru.job4j.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.*;
import ru.job4j.model.*;
import ru.job4j.service.*;

import java.lang.reflect.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final SpringPersonService persons;
    private final SpringAddressService addressService;
    private BCryptPasswordEncoder encoder;
    private static final Logger LOGGER = LogManager.getLogger(PersonController.class.getName());
    private final ObjectMapper objectMapper;

    public PersonController(final SpringPersonService persons,
                            SpringAddressService addressService,
                            ObjectMapper objectMapper,
                            BCryptPasswordEncoder encoder) {
        this.persons = persons;
        this.addressService = addressService;
        this.objectMapper = objectMapper;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<Person>(
                person.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. Please, check requisites."
                )),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody PersonDTO personDTO) {
        var login = personDTO.getLogin();
        var password = personDTO.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        var person = new Person();
        person.setLogin(login);
        person.setPassword(encoder.encode(password));
        var address = addressService.findById(personDTO.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        person.setAddress(address);
        return new ResponseEntity<Person>(
                this.persons.create(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody PersonDTO personDTO) {
        var login = personDTO.getLogin();
        var password = personDTO.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        var person = new Person();
        person.setId(personDTO.getId());
        person.setLogin(login);
        person.setPassword(encoder.encode(password));
        var address = addressService.findById(personDTO.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        person.setAddress(address);
        if (this.persons.save(person)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        if (this.persons.delete(person)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
