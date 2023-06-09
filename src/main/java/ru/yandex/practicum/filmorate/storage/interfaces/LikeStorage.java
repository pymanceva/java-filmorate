package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface LikeStorage {

    void add(Long filmId, Long userId);

    void delete(Long filmId, Long userId);

    Collection<Long> getAll();

    boolean contains(Long filmId, Long userId);

    Collection<Long> getLikes(Long filmId);

    Collection<Film> getMostPopularFilmsByLikes(long count);

}
