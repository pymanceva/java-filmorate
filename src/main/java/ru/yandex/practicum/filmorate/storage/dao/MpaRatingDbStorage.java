package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.dao.mapper.mapper.MpaRatingMapper;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@Repository
@Primary
@SuppressWarnings("unused")
public class MpaRatingDbStorage implements MpaRatingStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaRatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MpaRating add(MpaRating mpaRating) {
        String sqlQuery = "INSERT INTO mpa_ratings (name) " +
                "VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, mpaRating.getName());
            return stmt;
        }, keyHolder);

        mpaRating.setId(keyHolder.getKey().longValue());
        return mpaRating;
    }

    @Override
    public MpaRating update(MpaRating mpaRating) {
        String sqlQuery = "UPDATE mpa_ratings SET name = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, mpaRating.getName());
        return mpaRating;
    }

    @Override
    public MpaRating delete(MpaRating mpaRating) {
        String sqlQuery = "DELETE FROM mpa_ratings WHERE id = ?";
        jdbcTemplate.update(sqlQuery, mpaRating.getId());
        return mpaRating;
    }

    @Override
    public boolean contains(Long id) {
        String sqlQuery = "SELECT count(*) from mpa_ratings where id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return result == 1;
    }

    @Override
    public MpaRating getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM mpa_ratings WHERE id=?",
                new MpaRatingMapper(), id);
    }

    @Override
    public List<MpaRating> getAll() {
        return jdbcTemplate.query("SELECT * FROM mpa_ratings ORDER BY id", new MpaRatingMapper());
    }
}
