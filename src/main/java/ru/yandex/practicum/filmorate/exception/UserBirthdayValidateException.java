package ru.yandex.practicum.filmorate.exception;

public class UserBirthdayValidateException extends ValidationException {

    public UserBirthdayValidateException() {
        super("Дата рождения не может быть в будущем");
    }
}

