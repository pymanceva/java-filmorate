package ru.yandex.practicum.filmorate.storage.inmemory;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("inMemoryFilmStorage")
@Getter
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film add(@NonNull @Valid Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(@NonNull @Valid Film film) {
        films.replace(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(@NonNull @Valid Film film) {
        films.remove(film.getId());
        return film;
    }

    @Override
    public boolean contains(Long id) {
        return films.containsKey(id);
    }

    @Override
    public Film getById(Long id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }
}
