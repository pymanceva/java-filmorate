package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
public class InMemoryMpaRatingStorage implements MpaRatingStorage{

    private final Map<Long, MpaRating> mpaRatings = new HashMap<>();

    @Override
    public Map<Long, MpaRating> getMpaRatings() {
        return mpaRatings;
    }

    @Override
    public void addMpaRating(MpaRating mpaRating) {
        mpaRatings.put(mpaRating.getId(), mpaRating);
    }

    @Override
    public void updateMpaRating(MpaRating mpaRating) {
        mpaRatings.replace(mpaRating.getId(), mpaRating);
    }

    @Override
    public void deleteMpaRating(MpaRating mpaRating) {
        mpaRatings.remove(mpaRating.getId(), mpaRating);
    }

    @Override
    public MpaRating getMpaRatingById(Long id) {
        return mpaRatings.get(id);
    }

    @Override
    public Collection<MpaRating> getAllMpaRatings() {
        return mpaRatings.values();
    }
}
