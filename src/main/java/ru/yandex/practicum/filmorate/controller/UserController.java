package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserUpdateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UserIDGenerator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody @Valid @NonNull User user) {
        if (users.containsValue(user)) {
            log.warn("Добавление существующего пользователя " + user);
            throw new UserAlreadyExistException();
        } else {
            log.debug("Добавлен пользователь: " + user);
            user.setId(UserIDGenerator.generateUserId());
            UserValidator.validate(user);
            users.put(user.getId(), user);
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid @NonNull User user) {
        if (users.containsKey(user.getId())) {
            UserValidator.validate(user);
            users.replace(user.getId(), user);
            log.debug("Пользователь id " + user.getId() + " обновлен");
        } else {
            throw new UserUpdateException();
        }

        return user;
    }
}
