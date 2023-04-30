package ru.yandex.practicum.filmorate.exception;

public class FilmDurationValidateException extends RuntimeException {

    public FilmDurationValidateException() {
        super("Продолжительность фильма не может быть отрицательной");
    }
}
