package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void addFriend(Long id, Long friendID);

    void deleteFriend(Long id, Long friendID);

    List<User> getFriends(Long id);

    Collection<User> getCommonFriends(Long id, Long otherId);

    User addUser(User user);

    User updateUser(User user);

    User deleteUser(User user);

    User getUserById(Long id);

    Collection<User> getAllUsers();
}