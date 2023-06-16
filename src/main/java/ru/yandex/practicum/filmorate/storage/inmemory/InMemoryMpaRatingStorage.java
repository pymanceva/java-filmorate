package ru.yandex.practicum.filmorate.storage.inmemory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("inMemoryMpaRatingStorage")
@Getter
@Slf4j
public class InMemoryMpaRatingStorage implements MpaRatingStorage {

    private final Map<Long, MpaRating> mpaRatings = new HashMap<>();

    @Override
    public MpaRating add(MpaRating mpaRating) {
        mpaRatings.put(mpaRating.getId(), mpaRating);
        return mpaRating;
    }

    @Override
    public MpaRating update(MpaRating mpaRating) {
        mpaRatings.replace(mpaRating.getId(), mpaRating);
        return mpaRating;
    }

    @Override
    public MpaRating delete(MpaRating mpaRating) {
        mpaRatings.remove(mpaRating.getId(), mpaRating);
        return mpaRating;
    }

    @Override
    public boolean contains(Long id) {
        return mpaRatings.containsKey(id);
    }

    @Override
    public MpaRating getById(Long id) {
        return mpaRatings.get(id);
    }

    @Override
    public List<MpaRating> getAll() {
        return (List<MpaRating>) mpaRatings.values();
    }
}
