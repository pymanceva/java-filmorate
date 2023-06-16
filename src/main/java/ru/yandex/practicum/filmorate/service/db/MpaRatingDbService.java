package ru.yandex.practicum.filmorate.service.db;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaRatingAlreadyExistException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.inmemory.InMemoryMpaRatingService;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;

import java.util.List;

@Service
@Getter
@Slf4j
@Primary
@SuppressWarnings("unused")
public class MpaRatingDbService extends InMemoryMpaRatingService {

    public MpaRatingDbService(MpaRatingStorage mpaRatingStorage) {
        super(mpaRatingStorage);
    }

    @Override
    public MpaRating addMpaRating(@NonNull MpaRating mpaRating) {
        if (mpaRatingStorage.contains(mpaRating.getId())) {
            log.warn("Добавление существующего рейтинга " + mpaRating);
            throw new MpaRatingAlreadyExistException();
        } else {
            mpaRatingStorage.add(mpaRating);
            log.debug("Добавлен рейтинг: " + mpaRating);
        }
        return mpaRating;
    }

    @Override
    public MpaRating updateMpaRating(@NonNull MpaRating mpaRating) {
        return super.updateMpaRating(mpaRating);
    }

    @Override
    public MpaRating deleteMpaRating(@NonNull MpaRating mpaRating) {
        return super.deleteMpaRating(mpaRating);
    }

    @Override
    public MpaRating getMpaRatingById(Long id) {
        return super.getMpaRatingById(id);
    }

    @Override
    public List<MpaRating> getAllMpaRatings() {
        return super.getAllMpaRatings();
    }
}
