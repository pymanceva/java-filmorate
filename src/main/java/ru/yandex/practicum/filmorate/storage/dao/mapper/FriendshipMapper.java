package ru.yandex.practicum.filmorate.storage.dao.mapper.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipMapper implements RowMapper<Friendship> {

    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friendship friendship = new Friendship();
        friendship.setUserId(rs.getLong("user_id"));
        friendship.setFriendId(rs.getLong("friend_id"));
        return friendship;
    }
}
