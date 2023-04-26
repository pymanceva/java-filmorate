package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.UserBirthdayValidateException;
import ru.yandex.practicum.filmorate.exception.UserEmailValidateException;
import ru.yandex.practicum.filmorate.exception.UserLoginValidateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {

    public static void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public static boolean manualValidate(User user) {
        return validateEmail(user.getEmail()) &&
                validateLogin(user.getLogin()) &&
                validateBirthday(user.getBirthday());
    }

    private static boolean validateEmail(String email) {
        if (email.isBlank() || email.contains("@")) {
            throw new UserEmailValidateException();
        }
        return true;
    }

    private static boolean validateLogin(String login) {
        if (login.isBlank() || login.contains(" ")) {
            throw new UserLoginValidateException();
        }
        return true;
    }

    private static boolean validateBirthday(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            throw new UserBirthdayValidateException();
        }
        return true;
    }


}
