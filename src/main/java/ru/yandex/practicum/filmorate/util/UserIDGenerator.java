package ru.yandex.practicum.filmorate.util;

public class UserIDGenerator {
    public static int id = 0;

    public static int generateUserId() {
        return ++id;
    }
}
