package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Objects;

@Component
@Repository
@Primary
@SuppressWarnings("unused")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        String sqlQuery = "INSERT INTO users (LOGIN, NAME, EMAIL, BIRTHDAY) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlQuery, new String[]{"id"}
            );
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setObject(4, user.getBirthday());

            return preparedStatement;
        }, keyHolder);

        long userId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        user.setId(userId);

        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET " +
                "LOGIN = ?, EMAIL = ?, BIRTHDAY = ?, NAME = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getLogin(), user.getEmail(), user.getBirthday(), user.getName(), user.getId());
        return user;
    }

    @Override
    public User delete(User user) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
        return user;
    }

    @Override
    public boolean contains(Long id) {
        String sqlQuery = "SELECT count(*) from users where id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return result == 1;
    }

    @Override
    public User getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new UserMapper(), id);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }
}
