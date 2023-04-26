package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    @NotBlank
    final private String login;
    @Email
    final private String email;
    @Past
    final private LocalDate birthday;
    private int id;
    private String name;
}
