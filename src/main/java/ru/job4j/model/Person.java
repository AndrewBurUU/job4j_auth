package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private int id;

    @NotBlank(message = "Login must be not empty")
    private String login;

    @NotNull(message = "Password length must be more than 5 characters.")
    @Size(min = 6)
    private String password;
}