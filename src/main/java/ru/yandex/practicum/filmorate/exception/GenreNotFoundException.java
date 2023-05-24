package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
        super("Жанр не найден");
    }

}
