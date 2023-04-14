package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Address;
import ru.job4j.repository.AddressRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class SpringAddressService {

    private final AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Optional<Address> findById(int id) {
        return addressRepository.findById(id);
    }

}
