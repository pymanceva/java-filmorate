package ru.yandex.practicum.filmorate.exception;

public class MpaRatingAlreadyExistException extends RuntimeException {

    public MpaRatingAlreadyExistException() {
        super("Рейтинг MPA уже был добавлен ранее");
    }
}
