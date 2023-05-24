package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;

public interface GenreStorage {

    Map<Long, Genre> getGenres();

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenre(Genre genre);

    Genre getGenreById(Long id);

    Collection<Genre> getAllGenres();
}
