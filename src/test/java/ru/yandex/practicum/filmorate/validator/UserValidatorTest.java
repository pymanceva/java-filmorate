package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.UserBirthdayValidateException;
import ru.yandex.practicum.filmorate.exception.UserEmailValidateException;
import ru.yandex.practicum.filmorate.exception.UserLoginValidateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login");
        user.setEmail("email@mail.ru");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        user.setName("name");
    }

    @Test
    void validateShouldSetLoginAsNameWhenNameIsNull() {
        user.setName(null);
        UserValidator.validate(user);
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    void validateShouldSetLoginAsNameWhenNameIsBlank() {
        user.setName(" ");
        UserValidator.validate(user);
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    void manualValidateShouldReturnTrueWhenUserDataIsCorrectAndNameFromUser() {
        boolean result = UserValidator.manualValidate(user);
        assertTrue(result);
        assertEquals("name", user.getName());
    }

    @Test
    void manualValidateShouldReturnTrueWhenUserDataIsCorrectAndNameIsBlank() {
        user.setName(" ");
        boolean result = UserValidator.manualValidate(user);
        assertTrue(result);
        assertEquals("login", user.getName());
    }

    @Test
    void manualValidateShouldReturnTrueWhenUserDataIsCorrectAndNameIsNull() {
        user.setName(null);
        boolean result = UserValidator.manualValidate(user);
        assertTrue(result);
        assertEquals("login", user.getName());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenEmailIsNull() {
        user.setEmail(null);
        UserEmailValidateException e = assertThrows(UserEmailValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Введен неверный e-mail", e.getMessage());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenEmailIsBlank() {
        user.setEmail(" ");
        UserEmailValidateException e = assertThrows(UserEmailValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Введен неверный e-mail", e.getMessage());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenEmailHasNoAt() {
        user.setEmail("wrong-email");
        UserEmailValidateException e = assertThrows(UserEmailValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Введен неверный e-mail", e.getMessage());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenLoginIsNull() {
        user.setLogin(null);
        UserLoginValidateException e = assertThrows(UserLoginValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenLoginIsBlank() {
        user.setLogin(" ");
        UserLoginValidateException e = assertThrows(UserLoginValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenLoginContainsSpace() {
        user.setLogin("log in");
        UserLoginValidateException e = assertThrows(UserLoginValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void manualValidateShouldThrowExceptionWhenBirthdayIsInFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        UserBirthdayValidateException e = assertThrows(UserBirthdayValidateException.class,
                () -> UserValidator.manualValidate(user));
        assertEquals("Дата рождения не может быть в будущем", e.getMessage());
    }
}