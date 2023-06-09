package ru.yandex.practicum.filmorate.service.inmemory;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaRatingAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.MpaRatingNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.interfaces.MpaRatingService;
import ru.yandex.practicum.filmorate.storage.interfaces.MpaRatingStorage;
import ru.yandex.practicum.filmorate.util.MpaRatingIdGenerator;

import javax.validation.Valid;
import java.util.List;

@Service
@Getter
@Slf4j
@SuppressWarnings("unused")
public class InMemoryMpaRatingService implements MpaRatingService {

    protected final MpaRatingStorage mpaRatingStorage;

    @Autowired
    public InMemoryMpaRatingService(MpaRatingStorage mpaRatingStorage) {
        this.mpaRatingStorage = mpaRatingStorage;
    }

    @Override
    public MpaRating addMpaRating(@NonNull @Valid MpaRating mpaRating) {
        if (mpaRatingStorage.contains(mpaRating.getId())) {
            log.warn("Добавление существующего рейтинга " + mpaRating);
            throw new MpaRatingAlreadyExistException();
        } else {
            mpaRating.setId(MpaRatingIdGenerator.incrementAndGetMpaRatingId());
            mpaRatingStorage.add(mpaRating);
            log.debug("Добавлен рейтинг: " + mpaRating);
        }
        return mpaRating;
    }

    @Override
    public MpaRating updateMpaRating(@NonNull @Valid MpaRating mpaRating) {
        if (mpaRatingStorage.contains(mpaRating.getId())) {
            mpaRatingStorage.update(mpaRating);
            log.debug("Рейтинг обновлен: " + mpaRating);
        } else {
            log.warn("Обновление несуществующего рейтинга " + mpaRating);
            throw new MpaRatingNotFoundException();
        }
        return mpaRating;
    }

    @Override
    public MpaRating deleteMpaRating(@NonNull @Valid MpaRating mpaRating) {
        if (!mpaRatingStorage.contains(mpaRating.getId())) {
            log.warn("Удаление несуществующего рейтинга " + mpaRating);
            throw new MpaRatingNotFoundException();
        } else {
            mpaRatingStorage.delete(mpaRating);
            log.debug("Рейтинг удален: " + mpaRating);
        }
        return mpaRating;
    }

    @Override
    public MpaRating getMpaRatingById(Long id) {
        if (!mpaRatingStorage.contains(id)) {
            log.warn("Запрос несуществующего рейтинга " + id);
            throw new MpaRatingNotFoundException();
        } else {
            log.trace("Получен рейтинг " + id);
            return mpaRatingStorage.getById(id);
        }
    }

    @Override
    public List<MpaRating> getAllMpaRatings() {
        log.debug("Текущее количество жанров: {}", mpaRatingStorage.getAll().size());
        return mpaRatingStorage.getAll();
    }
}
