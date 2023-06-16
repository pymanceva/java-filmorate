package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;

public interface GenreStorage extends Storage<Genre> {

    Collection<Genre> getGenresOfFilm(Long filmId);

    Collection<Genre> addGenresOfFilm(Film film);

    Collection<Genre> updateGenresOfFilm(Film film);

    Map<Long, Collection<Genre>> getAllFilmGenres(Collection<Film> films);
}
