package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, Date> {
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date != null) {
            return date.toLocalDate().isAfter(FilmValidator.getReleaseDateMin().minusDays(1));
        }
        return false;
    }
}
