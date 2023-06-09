package ru.yandex.practicum.filmorate.service.db;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.inmemory.InMemoryFilmService;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.HashSet;

@Service
@Getter
@Slf4j
@Primary
public class FilmDbService extends InMemoryFilmService {

    private final GenreStorage genreStorage;
    private final MpaRatingStorage mpaRatingStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmDbService(FilmStorage filmStorage, UserStorage userStorage,
                         GenreStorage genreStorage, MpaRatingStorage mpaRatingStorage, LikeStorage likeStorage) {
        super(filmStorage, userStorage);
        this.genreStorage = genreStorage;
        this.mpaRatingStorage = mpaRatingStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public void addLike(@NonNull Long id, Long userId) {
        if (!filmStorage.contains(id)) {
            log.warn("Добавление лайка несуществующему фильму " + id);
            throw new FilmNotFoundException();
        } else if (!userStorage.contains(userId)) {
            log.warn("Добавление лайка от несуществующего пользователя " + userId);
            throw new UserNotFoundException();
        } else {
            likeStorage.add(id, userId);
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
            likeStorage.delete(id, userId);
            log.debug("Удален лайк от " + userId + " фильму " + id);
        }
    }

    @Override
    public Collection<Film> getMostPopularFilmsByLikes(int count) {
        return likeStorage.getMostPopularFilmsByLikes(count);
    }

    @Override
    public Film addFilm(@NonNull Film film) {
        if (filmStorage.contains(film.getId())) {
            log.warn("Добавление существующего фильма " + film);
            throw new FilmAlreadyExistException();
        } else {
            filmStorage.add(film);
            log.debug("Добавлен фильм: " + film);
        }
        return film;
    }

    @Override
    public Film updateFilm(@NonNull Film film) {
        return super.updateFilm(film);
    }

    @Override
    public Film deleteFilm(@NonNull Film film) {
        return super.deleteFilm(film);
    }

    @Override
    public Film getFilmById(Long id) {
        return super.getFilmById(id);
    }

    @Override
    public Collection<Film> getAllFilms() {
        Collection<Film> films = super.getAllFilms();
        for (Film film : films) {
            Collection<Long> genresId = genreStorage.getGenresOfFilm(film.getId());
            Collection<Genre> genres = new HashSet<>();
            for (Long genreId : genresId) {
                genres.add(genreStorage.getById(genreId));
            }
            film.setGenres(genres);
            film.setMpa(mpaRatingStorage.getById(film.getMpa().getId()));
        }
        return films;
    }
}
