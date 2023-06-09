package ru.yandex.practicum.filmorate.storage.interfaces.inmemory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Component("inMemoryGenreStorage")
@Getter
@Slf4j
public class InMemoryGenreStorage implements GenreStorage {

    private final Map<Long, Genre> genres = new HashMap<>();
    private final FilmStorage filmStorage;

    @Autowired
    public InMemoryGenreStorage(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public Genre add(Genre genre) {
        genres.put(genre.getId(), genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        genres.replace(genre.getId(), genre);
        return genre;
    }

    @Override
    public Genre delete(Genre genre) {
        genres.remove(genre.getId());
        return genre;
    }

    @Override
    public boolean contains(Long id) {
        return genres.containsKey(id);
    }

    @Override
    public Genre getById(Long id) {
        return genres.get(id);
    }

    @Override
    public Collection<Genre> getAll() {
        return genres.values();
    }

    @Override
    public Collection<Long> getGenresOfFilm(Long filmId) {
        return filmStorage.getById(filmId).getGenres()
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Genre> addGenresOfFilm(Film film) {
        return new HashSet<>();
    }

    @Override
    public Collection<Genre> updateGenresOfFilm(Film film) {
        return new HashSet<>();
    }
}
