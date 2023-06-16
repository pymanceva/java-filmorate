package ru.yandex.practicum.filmorate.storage.inmemory;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("inMemoryUserStorage")
@Getter
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User add(@NonNull @Valid User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(@NonNull @Valid User user) {
        users.replace(user.getId(), user);
        return user;
    }

    @Override
    public User delete(@NonNull @Valid User user) {
        users.remove(user.getId());
        return user;
    }

    @Override
    public boolean contains(Long id) {
        return users.containsKey(id);
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }
}
