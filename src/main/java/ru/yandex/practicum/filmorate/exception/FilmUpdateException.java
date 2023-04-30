package ru.yandex.practicum.filmorate.exception;

public class FilmUpdateException extends RuntimeException {

    public FilmUpdateException() {
        super("Обновление фильма, отсутствующего в коллекции");
    }

}
