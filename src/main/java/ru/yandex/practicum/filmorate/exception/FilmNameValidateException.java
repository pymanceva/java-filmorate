package ru.yandex.practicum.filmorate.exception;

public class FilmNameValidateException extends ValidationException {

    public FilmNameValidateException() {
        super("Название фильма не может быть пустым");
    }
}
