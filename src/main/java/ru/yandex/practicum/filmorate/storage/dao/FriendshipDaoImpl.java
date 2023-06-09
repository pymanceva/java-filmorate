package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.FriendshipMapper;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Repository
@Primary
@SuppressWarnings("unused")
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO friendship (USER_ID, FRIEND_ID) " +
                "VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void delete(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public boolean contains(Long userId, Long friendId) {
        String sqlQuery = "SELECT count(*) FROM friendship WHERE user_id = ? AND friend_id = ?";
        int result = jdbcTemplate.queryForObject(sqlQuery, Integer.class, userId, friendId);
        return result == 1;
    }

    @Override
    public Friendship getById(Long userId, Long friendId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM friendship WHERE user_id = ? AND friend_id = ?"
                , new FriendshipMapper(), userId, friendId);
    }

    @Override
    public Collection<Long> getAllFriendsOfUser(Long userId) {
        return jdbcTemplate.query("SELECT * FROM friendship WHERE user_id = ?",
                        new FriendshipMapper(), userId)
                .stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(Long id, Long otherId) {
        String sql = "SELECT u.id, u.email, u.login, u.name, u.birthday " +
                "FROM friendship f1 " +
                "JOIN friendship f2 ON f1.friend_id = f2.friend_id " +
                "JOIN users u ON f1.friend_id = u.id " +
                "WHERE f1.user_id = ? AND f2.user_id = ?";
        return jdbcTemplate.query(sql, ps -> {
            ps.setLong(1, id);
            ps.setLong(2, otherId);
        }, new UserMapper());
    }
}
