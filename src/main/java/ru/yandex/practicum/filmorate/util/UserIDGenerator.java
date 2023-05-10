package ru.yandex.practicum.filmorate.util;

public class UserIDGenerator {
    private static long id = 0;

    public static long incrementAndGetUserId() {
        return ++id;
    }
}
