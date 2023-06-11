package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Objects;

@Component
@Repository
@Primary
@SuppressWarnings("unused")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaRatingStorage mpaRatingStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmDbStorage(
            JdbcTemplate jdbcTemplate,
            MpaRatingStorage mpaRatingStorage,
            GenreStorage genreStorage,
            LikeStorage likeStorage
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaRatingStorage = mpaRatingStorage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public Film add(Film film) {
        String sqlQuery = "INSERT INTO films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_RATING_ID) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlQuery, new String[]{"id"});
            preparedStatement.setString(1, film.getName());
            preparedStatement.setString(2, film.getDescription());
            preparedStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
            preparedStatement.setInt(4, film.getDuration());
            preparedStatement.setLong(5, film.getMpa().getId());
            return preparedStatement;
        }, keyHolder);

        long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        film.setId(filmId);
        film.setMpa(mpaRatingStorage.getById(film.getMpa().getId()));
        genreStorage.updateGenresOfFilm(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET " +
                "NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?,  MPA_RATING_ID = ?" +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
        film.setMpa(mpaRatingStorage.getById(film.getMpa().getId()));
        film.setGenres(genreStorage.updateGenresOfFilm(film));
        return film;
    }

    @Override
    public Film delete(Film film) {
        String sqlQuery = "DELETE FROM films WHERE id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        return film;
    }

    @Override
    public boolean contains(Long id) {
        String sqlQuery = "SELECT count(*) from films where id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return result == 1;
    }

    @Override
    public Film getById(Long id) {
        Film film = jdbcTemplate.queryForObject(
                "SELECT * FROM films WHERE id=?", new FilmMapper(), id);
        film.setMpa(mpaRatingStorage.getById(film.getMpa().getId()));
        film.setGenres(genreStorage.getGenresOfFilm(id));
        film.setLikes(likeStorage.getLikes(id));
        return film;
    }

    @Override
    public Collection<Film> getAll() {
        return jdbcTemplate.query("SELECT * FROM films", new FilmMapper());
    }
}
