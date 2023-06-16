package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

@SuppressWarnings("unused")
public interface MpaRatingService {

    MpaRating addMpaRating(MpaRating mpaRating);

    MpaRating updateMpaRating(MpaRating mpaRating);

    MpaRating deleteMpaRating(MpaRating mpaRating);

    MpaRating getMpaRatingById(Long id);

    List<MpaRating> getAllMpaRatings();
}
