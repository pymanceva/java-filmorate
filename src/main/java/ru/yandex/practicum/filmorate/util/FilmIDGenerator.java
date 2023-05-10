package ru.yandex.practicum.filmorate.util;

public class FilmIDGenerator {
    private static long id = 0;

    public static long incrementAndGetFilmId() {
        return ++id;
    }

}
