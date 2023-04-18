package ru.job4j.dto;

import lombok.*;

import javax.validation.constraints.*;

import ru.job4j.model.Operation;

@Data
public class PersonDTO {
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
