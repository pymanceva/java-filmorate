package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class MpaRating {
    private Long id;
    @NonNull
    @NotBlank
    private String name;
}
