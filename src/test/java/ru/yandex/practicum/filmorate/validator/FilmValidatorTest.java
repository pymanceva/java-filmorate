package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmDescriptionValidateException;
import ru.yandex.practicum.filmorate.exception.FilmDurationValidateException;
import ru.yandex.practicum.filmorate.exception.FilmNameValidateException;
import ru.yandex.practicum.filmorate.exception.FilmReleaseDateValidateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidatorTest {

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("name");
        film.setDuration(90);
        film.setDescription("description");
        film.setReleaseDate(Date.valueOf("1895-12-28"));
    }

    @Test
    void validateShouldReturnTrueWhenFilmReleaseDateCorrect() {
        boolean result = FilmValidator.validate(film);
        assertTrue(result);
    }

    @Test
    void validateThrowExceptionWhenFilmReleaseDateIncorrect() {
        film.setReleaseDate(Date.valueOf(LocalDate.of(1895, 12, 27)));
        FilmReleaseDateValidateException e = assertThrows(FilmReleaseDateValidateException.class,
                () -> FilmValidator.validate(film));
        assertEquals("Дата релиза фильма не может быть раньше 28.12.1895", e.getMessage());
    }

    @Test
    void manualValidateShouldReturnTrueWhenFilmDataCorrect() {
        boolean result = FilmValidator.manualValidate(film);
        assertTrue(result);
    }

    @Test
    void manualValidateThrowExceptionWhenFilmNameIsBlank() {
        film.setName(" ");
        FilmNameValidateException e = assertThrows(FilmNameValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Название фильма не может быть пустым", e.getMessage());
    }

    @Test
    void manualValidateThrowExceptionWhenFilmNameIsNull() {
        film.setName(null);
        FilmNameValidateException e = assertThrows(FilmNameValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Название фильма не может быть пустым", e.getMessage());
    }

    @Test
    void manualValidateThrowExceptionWhenFilmDescriptionIsNull() {
        film.setDescription(null);
        FilmDescriptionValidateException e = assertThrows(FilmDescriptionValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Описание фильма должно быть от 1 до 200 символов", e.getMessage());
    }

    @Test
    void manualValidateThrowExceptionWhenFilmDescriptionIsBlank() {
        film.setDescription(" ");
        FilmDescriptionValidateException e = assertThrows(FilmDescriptionValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Описание фильма должно быть от 1 до 200 символов", e.getMessage());
    }

    @Test
    void manualValidateThrowExceptionWhenFilmDescriptionIs201symbol() {
        film.setDescription("Too long description of the film to pass validation successfully. " +
                "Too long description of the film to pass validation successfully. " +
                "Too long description of the film to pass validation successfully. End");
        FilmDescriptionValidateException e = assertThrows(FilmDescriptionValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Описание фильма должно быть от 1 до 200 символов", e.getMessage());
    }

    @Test
    void manualValidateThrowExceptionWhenFilmReleaseDateIncorrect() {
        film.setReleaseDate(Date.valueOf(LocalDate.of(1895, 12, 27)));
        FilmReleaseDateValidateException e = assertThrows(FilmReleaseDateValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Дата релиза фильма не может быть раньше 28.12.1895", e.getMessage());
    }

    @Test
    void manualValidateThrowExceptionWhenFilmDurationIsNegative() {
        film.setDuration(-1);
        FilmDurationValidateException e = assertThrows(FilmDurationValidateException.class,
                () -> FilmValidator.manualValidate(film));
        assertEquals("Продолжительность фильма не может быть отрицательной", e.getMessage());
    }
}