package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    @NotBlank
    private String name;
    private int id;
    private double rate;
    @Size(max = 200)
    private String description;

    private LocalDate releaseDate;
    @Positive
    private int duration;
}
