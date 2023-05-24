package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;

public interface MpaRatingService {

    MpaRating addMpaRating(MpaRating mpaRating);

    MpaRating updateMpaRating(MpaRating mpaRating);

    MpaRating deleteMpaRating(MpaRating mpaRating);

    MpaRating getMpaRatingById(Long id);

    Collection<MpaRating> getAllMpaRatings();
}
