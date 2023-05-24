package ru.yandex.practicum.filmorate.service.genre;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.util.GenreIDGenerator;

import javax.validation.Valid;
import java.util.Collection;

@Service
@Getter
@Slf4j
public class InMemoryGenreService implements GenreService{

    private final GenreStorage genreStorage;

    @Autowired
    public InMemoryGenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }


    @Override
    public Genre addGenre(@NonNull @Valid Genre genre) {
        if (genreStorage.getGenres().containsValue(genre)) {
            log.warn("Добавление существующего жанра " + genre);
            throw new GenreAlreadyExistException();
        } else {
            genre.setId(GenreIDGenerator.incrementAndGetGenreId());
            genreStorage.addGenre(genre);
            log.debug("Добавлен жанр: " + genre);
        }
        return genre;
    }

    @Override
    public Genre updateGenre(@NonNull @Valid Genre genre) {
        if (genreStorage.getGenres().containsValue(genre)) {
            genreStorage.updateGenre(genre);
            log.debug("Жанр обновлен: " + genre);
        } else {
            log.warn("Обновление несуществующего жанра " + genre);
            throw new GenreNotFoundException();
        }
        return genre;
    }

    @Override
    public Genre deleteGenre(@NonNull @Valid Genre genre) {
        if (!genreStorage.getGenres().containsValue(genre)) {
            log.warn("Удаление несуществующего жанра " + genre);
            throw new GenreNotFoundException();
        } else {
            genreStorage.deleteGenre(genre);
            log.debug("Жанр удален: " + genre);
        }
        return genre;
    }

    @Override
    public Genre getGenreById(Long id) {
        if (!genreStorage.getGenres().containsKey(id)) {
            log.warn("Запрос несуществующего жанра " + id);
            throw new GenreNotFoundException();
        } else {
            log.trace("Получен фильм " + id);
            return genreStorage.getGenreById(id);
        }
    }

    @Override
    public Collection<Genre> getAllGenres() {
        log.debug("Текущее количество жанров: {}", genreStorage.getGenres().size());
        return genreStorage.getAllGenres();
    }
}
