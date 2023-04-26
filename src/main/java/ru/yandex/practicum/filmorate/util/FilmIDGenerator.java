package ru.yandex.practicum.filmorate.util;

public class FilmIDGenerator {
    public static int id = 0;

    public static int generateFilmId() {
        return ++id;
    }

}
