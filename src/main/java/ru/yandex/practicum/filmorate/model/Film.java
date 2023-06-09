package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class Film {
    private long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @ReleaseDate
    private Date releaseDate;
    @Positive
    private int duration;
    private MpaRating mpa;
    private Collection<Genre> genres = new TreeSet<>(Comparator.comparingLong(Genre::getId));
    private Collection<Long> likes = new HashSet<>();
}
