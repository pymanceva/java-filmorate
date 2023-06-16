package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipStorage {

    void add(Long userId, Long friendId);

    void delete(Long userId, Long friendId);

    boolean contains(Long userId, Long friendId);

    Friendship getById(Long userId, Long friendId);

    Collection<Long> getAllFriendsOfUser(Long userId);

    Collection<User> getCommonFriends(Long id, Long otherId);
}
