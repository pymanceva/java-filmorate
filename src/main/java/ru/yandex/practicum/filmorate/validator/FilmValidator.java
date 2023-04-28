package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.FilmDescriptionValidateException;
import ru.yandex.practicum.filmorate.exception.FilmDurationValidateException;
import ru.yandex.practicum.filmorate.exception.FilmNameValidateException;
import ru.yandex.practicum.filmorate.exception.FilmReleaseDateValidateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static java.time.Month.DECEMBER;

public class FilmValidator {
    private static final int DESCRIPTION_LENGTH_MAX = 200;
    private static final LocalDate RELEASE_DATE_MIN = LocalDate.of(1895, DECEMBER, 28);

    /* Прошу дать немного обратной связи по соответсвию подобного деления функционала по классам принципам SOLID,
    о которых мы общались ранее в рамках 8 спринта.
     */
    public static LocalDate getReleaseDateMin() {
        return RELEASE_DATE_MIN;
    }

    public static int getDescriptionLengthMax() {
        return DESCRIPTION_LENGTH_MAX;
    }

    public static boolean validate(Film film) {
        return validateReleaseDate(film.getReleaseDate());
    }

    public static boolean manualValidate(Film film) {
        return validateName(film.getName()) &&
                validateDescription(film.getDescription()) &&
                validateReleaseDate(film.getReleaseDate()) &&
                validateDuration(film.getDuration());
    }

    private static boolean validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new FilmNameValidateException();
        }
        return true;
    }

    private static boolean validateDescription(String description) {
        if (description == null || description.isBlank() || description.length() > DESCRIPTION_LENGTH_MAX) {
            throw new FilmDescriptionValidateException();
        }
        return true;
    }

    private static boolean validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(RELEASE_DATE_MIN)) {
            throw new FilmReleaseDateValidateException();
        }
        return true;
    }

    private static boolean validateDuration(int duration) {
        if (duration < 0) {
            throw new FilmDurationValidateException();
        }
        return true;
    }
}