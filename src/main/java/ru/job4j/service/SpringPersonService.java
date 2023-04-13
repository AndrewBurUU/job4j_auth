package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import javax.transaction.*;
import java.util.*;
import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class SpringPersonService implements UserDetailsService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(person.getLogin(), person.getPassword(), emptyList());
    }

    @Transactional
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public boolean save(Person person) {
        var res = personRepository.findById(person.getId());
        if (res.isPresent()) {
            personRepository.save(person);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delete(Person person) {
        var res = personRepository.findById(person.getId());
        if (res.isPresent()) {
            personRepository.delete(person);
            return true;
        }
        return false;
    }
}
