package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.validator.FilmValidator;

public class FilmDescriptionValidateException extends RuntimeException {

    public FilmDescriptionValidateException() {
        super("Описание фильма не может быть больше " + FilmValidator.DESCRIPTION_LENGTH_MAX + " символов");
    }
}
