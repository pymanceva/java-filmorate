package ru.yandex.practicum.filmorate.util;

public class GenreIDGenerator {

    private static long id = 0;

    public static long incrementAndGetGenreId() {
        return ++id;
    }
}
