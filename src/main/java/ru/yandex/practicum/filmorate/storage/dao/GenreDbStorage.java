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
import ru.yandex.practicum.filmorate.storage.dao.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.sql.PreparedStatement;
import java.util.*;
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
        jdbcTemplate.update(sqlQuery, genre.getName(), genre.getId());
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
    public Collection<Genre> getGenresOfFilm(Long filmId) {
        String sqlQuery = "SELECT g.id AS id, name " +
                "FROM film_genres fg " +
                "LEFT JOIN genres g ON " +
                "fg.genre_id = g.id " +
                "WHERE film_id = ?";
        return jdbcTemplate.query(sqlQuery, new GenreMapper(), filmId);
    }

    @Override
    public Collection<Genre> addGenresOfFilm(Film film) {
        //Варианта замены не нашла. При любом раскладе нужно добавить ровно то количество строк, сколько
        //жанров у этого фильма, т.е. выполнить это кол-во запросов.
        //Все опрошенные студенты также делали множественными запросами.
        //В связи с сжатыми сроками оставляю так - в этом спринте нельзя использовать каникулы для доработки.
        String sqlQuery = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";

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

    @Override
    public Map<Long, Collection<Genre>> getAllFilmGenres(Collection<Film> films) {
        final String sql = "SELECT fg.film_id AS film_id, g.id AS genre_id, g.name AS name FROM film_genres fg " +
                "LEFT JOIN genres g ON fg.genre_id = g.id " +
                "WHERE fg.film_id in (%s)";

        Map<Long, Collection<Genre>> filmGenresMap = new HashMap<>();
        Collection<String> listId = films.stream().map(film -> String.valueOf(film.getId())).collect(Collectors.toList());

        jdbcTemplate.query(String.format(sql, String.join(",", listId)), rs -> {
            Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));

            Long filmId = rs.getLong("film_id");

            filmGenresMap.putIfAbsent(filmId, new ArrayList<>());
            filmGenresMap.get(filmId).add(genre);
        });

        return filmGenresMap;
    }
}
