package ru.yandex.practicum.filmorate.film.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface FilmStorage {

    Map<Long, Film> getFilms();

    void addFilm(Film film);

    void updateFilm(Film film);

    void deleteFilm(Film film);

    Film getFilmById(Long id);

    Collection<Film> getAllFilms();

}
