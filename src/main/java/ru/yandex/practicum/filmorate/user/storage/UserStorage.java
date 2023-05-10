package ru.yandex.practicum.filmorate.user.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {

    Map<Long, User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUserByID(Long ID);

    Collection<User> getAllUsers();
}
