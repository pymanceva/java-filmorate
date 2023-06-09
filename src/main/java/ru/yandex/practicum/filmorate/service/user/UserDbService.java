package ru.yandex.practicum.filmorate.service.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
@Primary
public class UserDbService extends InMemoryUserService {

    private final FriendshipDao friendshipDao;

    @Autowired

    public UserDbService(UserStorage userStorage, FriendshipDao friendshipDao) {
        super(userStorage);
        this.friendshipDao = friendshipDao;
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
            friendshipDao.add(id, friendId);
            log.debug("Пользователь " + id + " добавил в друзья пользователя " + friendId);
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
            friendshipDao.delete(id, friendId);
            log.debug("Пользователь " + id + " удалил из друзей пользователя " + friendId);
        }
    }

    @Override
    public List<User> getFriends(Long id) {
        return friendshipDao.getAllFriendsOfUser(id)
                .stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(Long id, Long otherId) {
        return friendshipDao.getCommonFriends(id, otherId);
    }

    @Override
    public User addUser(@NonNull User user) {
        if (userStorage.contains(user.getId())) {
            log.warn("Добавление существующего пользователя " + user);
            throw new UserAlreadyExistException();
        } else {
            UserValidator.validate(user);
            userStorage.add(user);
            log.debug("Добавлен пользователь: " + user);
        }
        return user;
    }

    @Override
    public User updateUser(@NonNull User user) {
        return super.updateUser(user);
    }

    @Override
    public User deleteUser(@NonNull User user) {
        return super.deleteUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return super.getUserById(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return super.getAllUsers();
    }
}
