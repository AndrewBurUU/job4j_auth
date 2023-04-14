package ru.job4j.dto;

import lombok.*;

@Data
public class PersonDTO {
    private int id;
    private String login;
    private String password;
    private int addressId;
}
