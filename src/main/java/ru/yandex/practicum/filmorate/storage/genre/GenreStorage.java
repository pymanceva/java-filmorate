package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

public interface GenreStorage extends Storage<Genre> {

    Collection<Long> getGenresOfFilm(Long filmId);

    Collection<Genre> addGenresOfFilm(Film film);

    Collection<Genre> updateGenresOfFilm(Film film);
}
