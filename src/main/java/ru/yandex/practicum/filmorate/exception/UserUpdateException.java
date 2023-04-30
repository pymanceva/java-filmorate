package ru.yandex.practicum.filmorate.exception;

public class UserUpdateException extends RuntimeException {

    public UserUpdateException() {
        super("Обновление пользователя, отсутствующего в коллекции");
    }

}
