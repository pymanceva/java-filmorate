package ru.yandex.practicum.filmorate.user.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.user.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.UserIDGenerator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InMemoryUserService implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public InMemoryUserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public void addFriend(@NonNull Long id, @NonNull Long friendId) {
        if (!userStorage.getUsers().containsKey(id)) {
            log.warn("Пользователь не найден: " + id);
            throw new UserNotFoundException();
        } else if (!userStorage.getUsers().containsKey(friendId)) {
            log.warn("Пользователь не найден: " + friendId);
            throw new UserNotFoundException();
        } else {
            Set<Long> friends = userStorage.getUserById(id).getFriends();
            friends.add(friendId);
            userStorage.getUserById(id).setFriends(friends);

            if (!userStorage.getUserById(friendId).getFriends().contains(id)) {
                addFriend(friendId, id);
            }

            log.debug("Добавлены в друзья (взаимно): " + id + ", " + friendId);
        }
    }

    @Override
    public void deleteFriend(@NonNull Long id, @NonNull Long friendId) {
        if (!userStorage.getUsers().containsKey(id)) {
            log.warn("Пользователь не найден: " + id);
            throw new UserNotFoundException();
        } else if (!userStorage.getUsers().containsKey(friendId)) {
            log.warn("Пользователь не найден: " + friendId);
            throw new UserNotFoundException();
        } else {
            Set<Long> friends = userStorage.getUsers().get(id).getFriends();
            friends.remove(userStorage.getUserById(friendId).getId());
            userStorage.getUsers().get(id).setFriends(friends);

            if (userStorage.getUserById(friendId).getFriends().contains(id)) {
                deleteFriend(friendId, id);
            }

            log.debug("Удалены из друзей (взаимно): " + id + ", " + friendId);
        }
    }

    @Override
    public List<User> getFriends(Long id) {
        return userStorage.getUserById(id).getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        Set<Long> friends1 = userStorage.getUserById(id).getFriends();
        Set<Long> friends2 = userStorage.getUserById(otherId).getFriends();
        return friends1.stream()
                .filter(friends2::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public User addUser(@NonNull @Valid User user) {
        if (userStorage.getUsers().containsValue(user)) {
            log.warn("Добавление существующего пользователя " + user);
            throw new UserAlreadyExistException();
        } else {
            user.setId(UserIDGenerator.incrementAndGetUserId());
            UserValidator.validate(user);
            userStorage.addUser(user);
            log.debug("Добавлен пользователь: " + user);
        }
        return user;
    }

    @Override
    public User updateUser(@NonNull @Valid User user) {
        if (userStorage.getUsers().containsKey(user.getId())) {
            UserValidator.validate(user);
            userStorage.updateUser(user);
            log.debug("Пользователь id " + user.getId() + " обновлен");
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User deleteUser(@NonNull @Valid User user) {
        if (userStorage.getUsers().containsKey(user.getId())) {
            userStorage.deleteUser(user);
            log.debug("Удален пользователь: " + user);
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        if (!userStorage.getUsers().containsKey(id)) {
            throw new UserNotFoundException();
        }
        log.trace("Получен пользователь " + id);
        return userStorage.getUserById(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        log.debug("Текущее количество пользователей: {}", userStorage.getUsers().size());
        return userStorage.getAllUsers();
    }
}
