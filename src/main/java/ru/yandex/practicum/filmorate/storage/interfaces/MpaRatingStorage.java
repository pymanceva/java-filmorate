package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaRatingStorage extends Storage<MpaRating> {

    @Override
    List<MpaRating> getAll();

}
