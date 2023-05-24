package ru.yandex.practicum.filmorate.storage.genre;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
public class InMemoryGenreStorage implements GenreStorage {

    private final Map<Long, Genre> genres = new HashMap<>();

    @Override
    public Map<Long, Genre> getGenres() {
        return genres;
    }

    @Override
    public void addGenre(Genre genre) {
        genres.put(genre.getId(), genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genres.replace(genre.getId(), genre);
    }

    @Override
    public void deleteGenre(Genre genre) {
        genres.remove(genre.getId());
    }

    @Override
    public Genre getGenreById(Long id) {
        return genres.get(id);
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return genres.values();
    }
}
