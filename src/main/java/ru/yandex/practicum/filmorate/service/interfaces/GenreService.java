package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@SuppressWarnings("unused")
public interface GenreService {

    Genre addGenre(Genre genre);

    Genre updateGenre(Genre genre);

    Genre deleteGenre(Genre genre);

    Genre getGenreById(Long id);

    Collection<Genre> getAllGenres();
}
