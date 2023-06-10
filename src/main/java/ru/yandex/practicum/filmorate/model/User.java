package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class User {
    private long id;
    @NotBlank
    private String login;
    @NotNull
    @Email
    private String email;
    @Past
    private LocalDate birthday;
    private String name;
    private Set<Long> friends = new HashSet<>();
    private Set<Friendship> friendshipsRequests = new HashSet<>();
}
