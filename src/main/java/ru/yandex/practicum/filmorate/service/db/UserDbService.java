package ru.yandex.practicum.filmorate.service.db;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.inmemory.InMemoryUserService;
import ru.yandex.practicum.filmorate.storage.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
@Primary
public class UserDbService extends InMemoryUserService {

    private final FriendshipStorage friendshipStorage;

    @Autowired

    public UserDbService(UserStorage userStorage, FriendshipStorage friendshipStorage) {
        super(userStorage);
        this.friendshipStorage = friendshipStorage;
    }

    @Override
    public void addFriend(@NonNull Long id, @NonNull Long friendId) {
        if (checkFriendsToAdd(id, friendId)) {
            friendshipStorage.add(id, friendId);
            log.debug("Пользователь " + id + " добавил в друзья пользователя " + friendId);
        } else {
            log.warn("Пользователь не найден");
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteFriend(@NonNull Long id, @NonNull Long friendId) {
        if (checkFriendsToAdd(id, friendId)) {
            friendshipStorage.delete(id, friendId);
            log.debug("Пользователь " + id + " удалил из друзей пользователя " + friendId);
        } else {
            log.warn("Пользователь не найден");
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getFriends(Long id) {
        return friendshipStorage.getAllFriendsOfUser(id)
                .stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(Long id, Long otherId) {
        return friendshipStorage.getCommonFriends(id, otherId);
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

    @Override
    public boolean checkFriendsToAdd(@NonNull Long id, @NonNull Long friendId) {
        Collection<User> users = userStorage.getAll();
        boolean ifUserExists = users.stream()
                .map(User::getId)
                .filter(userId -> userId.equals(id)).count() == 1;
        boolean ifFriendExists = users.stream()
                .map(User::getId)
                .filter(userId -> userId.equals(friendId)).count() == 1;

        return ifUserExists && ifFriendExists;
    }
}
