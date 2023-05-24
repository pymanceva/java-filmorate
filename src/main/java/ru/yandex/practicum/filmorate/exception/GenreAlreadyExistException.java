package ru.yandex.practicum.filmorate.exception;

public class GenreAlreadyExistException extends RuntimeException {

    public GenreAlreadyExistException() {
        super("Жанр уже был добавлен ранее");
    }
}
