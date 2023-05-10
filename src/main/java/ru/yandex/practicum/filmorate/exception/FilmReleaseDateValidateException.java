package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.format.DateTimeFormatter;

public class FilmReleaseDateValidateException extends ValidationException {

    public FilmReleaseDateValidateException() {
        super("Дата релиза фильма не может быть раньше " +
                FilmValidator.getReleaseDateMin().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
