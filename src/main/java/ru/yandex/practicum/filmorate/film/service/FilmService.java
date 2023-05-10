package ru.yandex.practicum.filmorate.film.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmService {

    void addLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);

    List<Film> getMostPopularFilmsByLikes(int count);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film deleteFilm(Film film);

    Film getFilmById(Long id);

    Collection<Film> getAllFilms();

}
