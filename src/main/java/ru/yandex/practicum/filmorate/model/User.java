package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private long id;
    @NotBlank
    private String login;
    @Email
    private String email;
    @Past
    private LocalDate birthday;
    private String name;
    private Set<Long> friends = new HashSet<>();
}
