package ru.yandex.practicum.filmorate.service.inmemory;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.interfaces.GenreService;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.util.GenreIDGenerator;

import javax.validation.Valid;
import java.util.Collection;

@Service
@Getter
@Slf4j
public class InMemoryGenreService implements GenreService {

    protected final GenreStorage genreStorage;

    @Autowired
    public InMemoryGenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }


    @Override
    public Genre addGenre(@NonNull @Valid Genre genre) {
        if (genreStorage.contains(genre.getId())) {
            log.warn("Добавление существующего жанра " + genre);
            throw new GenreAlreadyExistException();
        } else {
            genre.setId(GenreIDGenerator.incrementAndGetGenreId());
            genreStorage.add(genre);
            log.debug("Добавлен жанр: " + genre);
        }
        return genre;
    }

    @Override
    public Genre updateGenre(@NonNull @Valid Genre genre) {
        if (genreStorage.contains(genre.getId())) {
            genreStorage.update(genre);
            log.debug("Жанр обновлен: " + genre);
        } else {
            log.warn("Обновление несуществующего жанра " + genre);
            throw new GenreNotFoundException();
        }
        return genre;
    }

    @Override
    public Genre deleteGenre(@NonNull @Valid Genre genre) {
        if (!genreStorage.contains(genre.getId())) {
            log.warn("Удаление несуществующего жанра " + genre);
            throw new GenreNotFoundException();
        } else {
            genreStorage.delete(genre);
            log.debug("Жанр удален: " + genre);
        }
        return genre;
    }

    @Override
    public Genre getGenreById(Long id) {
        if (!genreStorage.contains(id)) {
            log.warn("Запрос несуществующего жанра " + id);
            throw new GenreNotFoundException();
        } else {
            log.trace("Получен фильм " + id);
            return genreStorage.getById(id);
        }
    }

    @Override
    public Collection<Genre> getAllGenres() {
        log.debug("Текущее количество жанров: {}", genreStorage.getAll().size());
        return genreStorage.getAll();
    }
}
