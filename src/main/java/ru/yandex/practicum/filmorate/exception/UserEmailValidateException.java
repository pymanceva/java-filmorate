package ru.yandex.practicum.filmorate.exception;

public class UserEmailValidateException extends RuntimeException {

    public UserEmailValidateException() {
        super("Введен неверный e-mail");
    }
}
