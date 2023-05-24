package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;
import java.util.Map;

public interface MpaRatingStorage {

    Map<Long, MpaRating> getMpaRatings();

    void addMpaRating(MpaRating mpaRating);

    void updateMpaRating(MpaRating mpaRating);

    void deleteMpaRating(MpaRating mpaRating);

    MpaRating getMpaRatingById(Long id);

    Collection<MpaRating> getAllMpaRatings();
}
