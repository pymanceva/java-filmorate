package ru.yandex.practicum.filmorate.exception;

public class MpaRatingNotFoundException extends RuntimeException {

    public MpaRatingNotFoundException() {
        super("Рейтинг MPA не найден");
    }
}
