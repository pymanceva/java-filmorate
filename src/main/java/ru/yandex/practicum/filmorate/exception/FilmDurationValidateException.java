package ru.yandex.practicum.filmorate.exception;

public class FilmDurationValidateException extends ValidationException {

    public FilmDurationValidateException() {
        super("Продолжительность фильма не может быть отрицательной");
    }
}
