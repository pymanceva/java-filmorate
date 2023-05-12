package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {

    Map<Long, User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUserById(Long id);

    Collection<User> getAllUsers();
}
