package ru.yandex.practicum.filmorate.service.film;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.FilmIDGenerator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
public class InMemoryFilmService implements FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public InMemoryFilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void addLike(@NonNull Long id, Long userId) {
        if (!filmStorage.getFilms().containsKey(id)) {
            log.warn("Добавление лайка несуществующему фильму " + id);
            throw new FilmNotFoundException();
        } else if (!userStorage.getUsers().containsKey(userId)) {
            log.warn("Добавление лайка от несуществующего пользователя " + userId);
            throw new UserNotFoundException();
        } else {
            Set<Long> likes = filmStorage.getFilmById(id).getLikes();
            likes.add(userId);
            filmStorage.getFilmById(id).setLikes(likes);
            log.debug("Добален лайк от " + userId + " фильму " + id);
        }
    }

    @Override
    public void deleteLike(@NonNull Long id, Long userId) {
        if (!filmStorage.getFilms().containsKey(id)) {
            log.warn("Удаление лайка у несуществующего фильма " + id);
            throw new FilmNotFoundException();
        } else if (!userStorage.getUsers().containsKey(userId)) {
            log.warn("Удаление лайка от несуществующего пользователя " + userId);
            throw new UserNotFoundException();
        } else {
            Set<Long> likes = filmStorage.getFilmById(id).getLikes();
            likes.remove(userId);
            filmStorage.getFilmById(id).setLikes(likes);
            log.debug("Удален лайк от " + userId + " фильму " + id);
        }
    }

    @Override
    public List<Film> getMostPopularFilmsByLikes(int count) {
        log.trace("Запрошен список " + count + " наиболее популярных фильмов");
        return filmStorage.getAllFilms()
                .stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film addFilm(@NonNull @Valid Film film) {
        if (filmStorage.getFilms().containsValue(film)) {
            log.warn("Добавление существующего фильма " + film);
            throw new FilmAlreadyExistException();
        } else {
            film.setId(FilmIDGenerator.incrementAndGetFilmId());
            filmStorage.addFilm(film);
            log.debug("Добавлен фильм: " + film);
        }
        return film;
    }

    @Override
    public Film updateFilm(@NonNull @Valid Film film) {
        if (filmStorage.getFilms().containsKey(film.getId())) {
            filmStorage.updateFilm(film);
            log.debug("Фильм обновлен: " + film);
        } else {
            log.warn("Обновление несуществующего фильма " + film);
            throw new FilmNotFoundException();
        }
        return film;
    }

    @Override
    public Film deleteFilm(@NonNull @Valid Film film) {
        if (!filmStorage.getFilms().containsValue(film)) {
            log.warn("Удаление несуществующего фильма " + film);
            throw new FilmNotFoundException();
        } else {
            filmStorage.deleteFilm(film);
            log.debug("Фильм удален: " + film);
        }
        return film;
    }

    @Override
    public Film getFilmById(Long id) {
        if (!filmStorage.getFilms().containsKey(id)) {
            log.warn("Запрос несуществующего фильма");
            throw new FilmNotFoundException();
        }
        log.trace("Получен фильм " + id);
        return filmStorage.getFilmById(id);
    }

    @Override
    public Collection<Film> getAllFilms() {
        log.debug("Текущее количество фильмов: {}", filmStorage.getFilms().size());
        return filmStorage.getAllFilms();
    }
}
