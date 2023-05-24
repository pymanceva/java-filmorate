package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreService {

    Genre addGenre(Genre genre);

    Genre updateGenre(Genre genre);

    Genre deleteGenre(Genre genre);

    Genre getGenreById(Long id);

    Collection<Genre> getAllGenres();
}
