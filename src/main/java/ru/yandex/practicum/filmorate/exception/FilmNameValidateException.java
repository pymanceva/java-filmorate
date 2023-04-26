package ru.yandex.practicum.filmorate.exception;

public class FilmNameValidateException extends RuntimeException {

    public FilmNameValidateException() {
        super("Название фильма не может быть пустым");
    }
}
