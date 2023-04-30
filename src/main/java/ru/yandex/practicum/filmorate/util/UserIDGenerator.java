package ru.yandex.practicum.filmorate.util;

public class UserIDGenerator {
    private static int id = 0;

    public static int incrementAndGetUserId() {
        return ++id;
    }
}
