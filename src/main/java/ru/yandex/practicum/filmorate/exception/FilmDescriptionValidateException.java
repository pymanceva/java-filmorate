package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.validator.FilmValidator;

public class FilmDescriptionValidateException extends RuntimeException {

    public FilmDescriptionValidateException() {
        super("Описание фильма должно быть от 1 до " + FilmValidator.getDescriptionLengthMax() + " символов");
    }
}
