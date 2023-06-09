package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mapper.GenreMapper;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Repository
@Primary
@SuppressWarnings("unused")
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre add(Genre genre) {
        String sqlQuery = "INSERT INTO genres (NAME) " +
                "VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, genre.getName());
            return stmt;
        }, keyHolder);

        genre.setId(keyHolder.getKey().longValue());
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        String sqlQuery = "UPDATE genres SET NAME = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, genre.getName());
        return genre;
    }

    @Override
    public Genre delete(Genre genre) {
        String sqlQuery = "DELETE FROM genres WHERE id = ?";
        jdbcTemplate.update(sqlQuery, genre.getId());
        return genre;
    }

    @Override
    public boolean contains(Long id) {
        String sqlQuery = "SELECT count(*) from genres where id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return result == 1;
    }

    @Override
    public Genre getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM genres WHERE id=?", new GenreMapper(), id);
    }

    @Override
    public Collection<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM genres ORDER BY id", new GenreMapper());
    }

    @Override
    public Collection<Long> getGenresOfFilm(Long filmId) {
        String sqlQuery = "SELECT GENRE_ID FROM FILM_GENRES WHERE FILM_ID = ?";
        return jdbcTemplate.queryForList(sqlQuery, Long.class, filmId);
    }

    @Override
    public Collection<Genre> addGenresOfFilm(Film film) {
        Collection<Genre> genres = new LinkedHashSet<>();
        if (film.getGenres() != null) {
            Set<Long> genresId = film.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toSet());
            for (Long id : genresId) {
                jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                        film.getId(), id);
                genres.add(new Genre(id, ""));
            }
        }
        return genres;
    }

    @Override
    public Collection<Genre> updateGenresOfFilm(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID = ?", film.getId());
        return addGenresOfFilm(film);
    }
}
