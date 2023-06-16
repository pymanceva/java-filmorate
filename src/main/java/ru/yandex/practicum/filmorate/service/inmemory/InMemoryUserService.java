package ru.yandex.practicum.filmorate.service.inmemory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.util.UserIDGenerator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public abstract class InMemoryUserService implements UserService {

    @Qualifier("inMemoryUserStorage")
    protected final UserStorage userStorage;

    @Autowired
    public InMemoryUserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public void addFriend(@NonNull Long id, @NonNull Long friendId) {
        if (!userStorage.contains(id)) {
            log.warn("Пользователь не найден: " + id);
            throw new UserNotFoundException();
        } else if (!userStorage.contains(friendId)) {
            log.warn("Пользователь не найден: " + friendId);
            throw new UserNotFoundException();
        } else {
            Set<Long> friends = userStorage.getById(id).getFriends();
            friends.add(friendId);
            userStorage.getById(id).setFriends(friends);

            if (!userStorage.getById(friendId).getFriends().contains(id)) {
                addFriend(friendId, id);
            }

            log.debug("Добавлены в друзья (взаимно): " + id + ", " + friendId);
        }
    }

    @Override
    public void deleteFriend(@NonNull Long id, @NonNull Long friendId) {
        if (!userStorage.contains(id)) {
            log.warn("Пользователь не найден: " + id);
            throw new UserNotFoundException();
        } else if (!userStorage.contains(friendId)) {
            log.warn("Пользователь не найден: " + friendId);
            throw new UserNotFoundException();
        } else {
            Set<Long> friends = userStorage.getById(id).getFriends();
            friends.remove(userStorage.getById(friendId).getId());
            userStorage.getById(id).setFriends(friends);

            if (userStorage.getById(friendId).getFriends().contains(id)) {
                deleteFriend(friendId, id);
            }

            log.debug("Удалены из друзей (взаимно): " + id + ", " + friendId);
        }
    }

    @Override
    public List<User> getFriends(Long id) {
        return userStorage.getById(id).getFriends().stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(Long id, Long otherId) {
        List<User> friends1 = getFriends(id);
        List<User> friends2 = getFriends(otherId);
        return friends1.stream()
                .filter(friends2::contains)
                .collect(Collectors.toList());
    }

    @Override
    public User addUser(@NonNull @Valid User user) {
        if (userStorage.contains(user.getId())) {
            log.warn("Добавление существующего пользователя " + user);
            throw new UserAlreadyExistException();
        } else {
            user.setId(UserIDGenerator.incrementAndGetUserId());
            UserValidator.validate(user);
            userStorage.add(user);
            log.debug("Добавлен пользователь: " + user);
        }
        return user;
    }

    @Override
    public User updateUser(@NonNull @Valid User user) {
        if (userStorage.contains(user.getId())) {
            UserValidator.validate(user);
            userStorage.update(user);
            log.debug("Пользователь id " + user.getId() + " обновлен");
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User deleteUser(@NonNull @Valid User user) {
        if (userStorage.contains(user.getId())) {
            userStorage.delete(user);
            log.debug("Удален пользователь: " + user);
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        if (!userStorage.contains(id)) {
            throw new UserNotFoundException();
        }
        log.trace("Получен пользователь " + id);
        return userStorage.getById(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        log.debug("Текущее количество пользователей: {}", userStorage.getAll().size());
        return userStorage.getAll();
    }

    public abstract boolean checkFriendsToAdd(@NonNull Long id, @NonNull Long friendId);
}
