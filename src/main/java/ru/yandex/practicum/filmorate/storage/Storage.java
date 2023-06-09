package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {

    T add(T element);

    T update(T element);

    T delete(T element);

    boolean contains(Long id);

    T getById(Long id);

    Collection<T> getAll();
}
