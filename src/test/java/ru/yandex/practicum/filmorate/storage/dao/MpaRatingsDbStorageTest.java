package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.MpaRating;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MpaRatingsDbStorageTest {

    private final MpaRatingDbStorage mpaRatingDbStorage;
    private MpaRating mpaRating;

    @BeforeEach
    void setUp() {
        mpaRating = new MpaRating();
        mpaRating.setName("Тестовый рейтинг");
    }

    @Test
    void shouldReturnRatingId6() {
        mpaRatingDbStorage.add(mpaRating);

        assertThat(mpaRatingDbStorage.getById(6L))
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("name", "Тестовый рейтинг");
    }

    @Test
    void shouldReturnRatingNameTest() {
        mpaRating.setId(1L);
        mpaRatingDbStorage.update(mpaRating);

        assertThat(mpaRatingDbStorage.getById(1L))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Тестовый рейтинг");
    }

    @Test
    void shouldReturnCollectionSize4() {
        mpaRating.setId(1L);
        mpaRating.setName("G");
        mpaRatingDbStorage.delete(mpaRating);

        assertThat(mpaRatingDbStorage.getAll()).hasSize(4);
    }

    @Test
    void shouldReturnTrueWhenContainsGenre() {
        assertTrue(mpaRatingDbStorage.contains(1L));
    }

    @Test
    void shouldReturnFalseWhenContainsNoGenre() {
        assertFalse(mpaRatingDbStorage.contains(10L));
    }

    @Test
    void shouldReturnCollectionSize5() {
        assertThat(mpaRatingDbStorage.getAll()).hasSize(5);
    }
}
