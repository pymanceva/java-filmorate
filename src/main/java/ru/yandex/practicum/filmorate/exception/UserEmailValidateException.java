package ru.yandex.practicum.filmorate.exception;

public class UserEmailValidateException extends ValidationException {

    public UserEmailValidateException() {
        super("Введен неверный e-mail");
    }
}
