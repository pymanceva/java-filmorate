package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.dao.mapper.LikeMapper;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.LikeStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Component
@Slf4j
@Repository
@Primary
@SuppressWarnings("unused")
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaRatingStorage mpaRatingStorage;
    private final GenreStorage genreStorage;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate, MpaRatingStorage mpaRatingStorage, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaRatingStorage = mpaRatingStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public void add(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO film_likes (FILM_ID, USER_ID) " +
                "VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void delete(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public Collection<Long> getAll() {
        return jdbcTemplate.query("SELECT * FROM film_likes", new LikeMapper());
    }

    @Override
    public boolean contains(Long filmId, Long userId) {
        String sqlQuery = "SELECT count(*) FROM film_likes WHERE film_id = ? AND user_id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, userId);
        return result == 1;
    }

    @Override
    public Collection<Long> getLikes(Long filmId) {
        String sqlQuery = "SELECT user_id FROM film_likes WHERE film_id = ?";
        return jdbcTemplate.query(sqlQuery, new LikeMapper(), filmId);
    }

    @Override
    public Collection<Film> getMostPopularFilmsByLikes(long count) {
        String sqlQuery = "SELECT films.*, mpa_rating_id " +
                "FROM films LEFT JOIN film_likes ON films.id = film_likes.film_id " +
                "GROUP BY films.id ORDER BY COUNT(film_likes.user_id) DESC LIMIT ?";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            Long filmId = rs.getLong("id");
            String filmName = rs.getString("name");
            String filmDescription = rs.getString("description");
            LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
            int duration = rs.getInt("duration");
            MpaRating mpa = mpaRatingStorage.getById(rs.getLong("mpa_rating_id"));
            Collection<Long> genresId = genreStorage.getGenresOfFilm(filmId);
            Collection<Genre> genres = new HashSet<>();
            for (Long genreId : genresId) {
                genres.add(genreStorage.getById(genreId));
            }
            Collection<Long> likes = getLikes(filmId);

            Film film = new Film();
            film.setId(filmId);
            film.setName(filmName);
            film.setDescription(filmDescription);
            film.setReleaseDate(releaseDate);
            film.setDuration(duration);
            film.setMpa(mpa);
            film.setGenres(genres);
            film.setLikes(likes);

            return film;
        }, count);
    }

    @Override
    public boolean filmAndUserExist(Long filmId, Long userId) {
        String sqlQuery = "SELECT COUNT(f.id), COUNT(u.id) " +
                "FROM films AS f " +
                "LEFT JOIN film_likes AS fl ON films.id = film_likes.film_id " +
                "FULL OUTER JOIN users AS u ON fl.user_id = u.id " +
                "WHERE f.id = ? AND u.id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, userId);
        int resultUSer = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, userId);
        return false;
    }
}
