package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

public interface LikeStorage {

    void add(Long filmId, Long userId) throws SQLIntegrityConstraintViolationException;

    void delete(Long filmId, Long userId);

    Collection<Long> getAll();

    boolean contains(Long filmId, Long userId);

    Collection<Long> getLikes(Long filmId);

    Collection<Film> getMostPopularFilmsByLikes(long count);

    boolean filmAndUserExist(Long filmId, Long userId);
}
