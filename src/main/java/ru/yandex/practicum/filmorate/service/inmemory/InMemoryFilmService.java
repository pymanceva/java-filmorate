package ru.yandex.practicum.filmorate.service.inmemory;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.util.FilmIDGenerator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
public class InMemoryFilmService implements FilmService {

    @Qualifier("inMemoryFilmStorage")
    protected final FilmStorage filmStorage;
    @Qualifier("inMemoryUserStorage")
    protected final UserStorage userStorage;

    public InMemoryFilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void addLike(@NonNull Long id, Long userId) {
        if (!filmStorage.contains(id)) {
            //комментарий в PR видела, однако это InMemory хранилища,
            // т.е. обращений к БД в рамках этого пакета не происходит.
            //В классах DAO конструкции с излишними запросами исправлены.
            log.warn("Добавление лайка несуществующему фильму " + id);
            throw new FilmNotFoundException();
        } else if (!userStorage.contains(userId)) {
            log.warn("Добавление лайка от несуществующего пользователя " + userId);
            throw new UserNotFoundException();
        } else {
            Collection<Long> likes = filmStorage.getById(id).getLikes();
            likes.add(userId);
            filmStorage.getById(id).setLikes(likes);
            log.debug("Добален лайк от " + userId + " фильму " + id);
        }
    }

    @Override
    public void deleteLike(@NonNull Long id, Long userId) {
        if (!filmStorage.contains(id)) {
            log.warn("Удаление лайка у несуществующего фильма " + id);
            throw new FilmNotFoundException();
        } else if (!userStorage.contains(userId)) {
            log.warn("Удаление лайка от несуществующего пользователя " + userId);
            throw new UserNotFoundException();
        } else {
            Collection<Long> likes = filmStorage.getById(id).getLikes();
            likes.remove(userId);
            filmStorage.getById(id).setLikes(likes);
            log.debug("Удален лайк от " + userId + " фильму " + id);
        }
    }

    @Override
    public Collection<Film> getMostPopularFilmsByLikes(int count) {
        log.trace("Запрошен список " + count + " наиболее популярных фильмов");
        return filmStorage.getAll()
                .stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film addFilm(@NonNull @Valid Film film) {
        if (filmStorage.contains(film.getId())) {
            log.warn("Добавление существующего фильма " + film);
            throw new FilmAlreadyExistException();
        } else {
            film.setId(FilmIDGenerator.incrementAndGetFilmId());
            filmStorage.add(film);
            log.debug("Добавлен фильм: " + film);
        }
        return film;
    }

    @Override
    public Film updateFilm(@NonNull @Valid Film film) {
        if (filmStorage.contains(film.getId())) {
            filmStorage.update(film);
            log.debug("Фильм обновлен: " + film);
        } else {
            log.warn("Обновление несуществующего фильма " + film);
            throw new FilmNotFoundException();
        }
        return film;
    }

    @Override
    public Film deleteFilm(@NonNull @Valid Film film) {
        if (!filmStorage.contains(film.getId())) {
            log.warn("Удаление несуществующего фильма " + film);
            throw new FilmNotFoundException();
        } else {
            filmStorage.delete(film);
            log.debug("Фильм удален: " + film);
        }
        return film;
    }

    @Override
    public Film getFilmById(Long id) {
        if (!filmStorage.contains(id)) {
            log.warn("Запрос несуществующего фильма");
            throw new FilmNotFoundException();
        }
        log.trace("Получен фильм " + id);
        return filmStorage.getById(id);
    }

    @Override
    public Collection<Film> getAllFilms() {
        log.debug("Текущее количество фильмов: {}", filmStorage.getAll().size());
        return filmStorage.getAll();
    }
}
