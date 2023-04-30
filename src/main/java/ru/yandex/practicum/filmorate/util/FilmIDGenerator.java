package ru.yandex.practicum.filmorate.util;

public class FilmIDGenerator {
    private static int id = 0;

    public static int incrementAndGetFilmId() {
        return ++id;
    }

}
