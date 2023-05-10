package ru.yandex.practicum.filmorate.user.storage;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public void addUser(@NonNull @Valid User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void updateUser(@NonNull @Valid User user) {
        users.replace(user.getId(), user);
    }

    @Override
    public void deleteUser(@NonNull @Valid User user) {
        users.remove(user.getId());
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
