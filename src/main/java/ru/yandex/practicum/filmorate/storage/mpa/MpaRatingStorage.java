package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public interface MpaRatingStorage extends Storage<MpaRating> {

    @Override
    List<MpaRating> getAll();

}
