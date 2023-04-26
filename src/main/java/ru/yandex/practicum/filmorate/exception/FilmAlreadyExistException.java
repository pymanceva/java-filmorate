package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyExistException extends RuntimeException {

    public FilmAlreadyExistException() {
        super("Фильм уже был добавлен ранее");
    }

}
