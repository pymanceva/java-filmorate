package ru.yandex.practicum.filmorate.exception;

public class UserLoginValidateException extends RuntimeException {

    public UserLoginValidateException() {
        super("Логин не может быть пустым и содержать пробелы");
    }
}

