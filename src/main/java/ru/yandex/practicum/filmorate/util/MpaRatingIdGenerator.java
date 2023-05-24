package ru.yandex.practicum.filmorate.util;

public class MpaRatingIdGenerator {

    private static long id = 0;

    public static long incrementAndGetMpaRatingId() {
        return ++id;
    }
}
