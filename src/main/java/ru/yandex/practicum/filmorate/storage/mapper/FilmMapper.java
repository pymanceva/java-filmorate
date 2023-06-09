package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(rs.getLong("mpa_rating_id"));

        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getNString("name"));
        film.setDescription(rs.getNString("description"));
        film.setReleaseDate(rs.getDate("release_date"));
        film.setDuration(rs.getInt("duration"));
        film.setMpa(mpaRating);
        return film;
    }
}