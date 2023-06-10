package ru.yandex.practicum.filmorate.storage.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getNString("login"));
        user.setEmail(rs.getNString("email"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        user.setName(rs.getNString("name"));
        return user;
    }
}


