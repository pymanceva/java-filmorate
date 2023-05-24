package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public void addFilm(@NonNull @Valid Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public void updateFilm(@NonNull @Valid Film film) {
        films.replace(film.getId(), film);
    }

    @Override
    public void deleteFilm(@NonNull @Valid Film film) {
        films.remove(film.getId());
    }

    @Override
    public Film getFilmById(Long id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }
}
