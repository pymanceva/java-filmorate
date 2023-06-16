package ru.yandex.practicum.filmorate.service.db;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.inmemory.InMemoryGenreService;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.Collection;

@Service
@Getter
@Slf4j
@Primary
@SuppressWarnings("unused")
public class GenreDbService extends InMemoryGenreService {

    @Autowired
    public GenreDbService(GenreStorage genreStorage) {
        super(genreStorage);
    }

    @Override
    public Genre addGenre(@NonNull Genre genre) {
        if (genreStorage.contains(genre.getId())) {
            log.warn("Добавление существующего жанра " + genre);
            throw new GenreAlreadyExistException();
        } else {
            genreStorage.add(genre);
            log.debug("Добавлен жанр: " + genre);
        }
        return genre;
    }

    @Override
    public Genre updateGenre(@NonNull Genre genre) {
        return super.updateGenre(genre);
    }

    @Override
    public Genre deleteGenre(@NonNull Genre genre) {
        return super.deleteGenre(genre);
    }

    @Override
    public Genre getGenreById(Long id) {
        return super.getGenreById(id);
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return super.getAllGenres();
    }
}
