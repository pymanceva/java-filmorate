package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank
    private String name;
    private double rate;
    @Size(max = 200)
    private String description;
    @ReleaseDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
