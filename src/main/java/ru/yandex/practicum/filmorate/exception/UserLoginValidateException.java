package ru.yandex.practicum.filmorate.exception;

public class UserLoginValidateException extends ValidationException {

    public UserLoginValidateException() {
        super("Логин не может быть пустым и содержать пробелы");
    }
}

