package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenreDbStorageTest {

    private final GenreDbStorage genreDbStorage;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setName("Тестовый жанр");
    }

    @Test
    void shouldReturnGenreId7() {
        genreDbStorage.add(genre);

        assertThat(genreDbStorage.getById(7L))
                .hasFieldOrPropertyWithValue("id", 7L)
                .hasFieldOrPropertyWithValue("name", "Тестовый жанр");
    }

    @Test
    void shouldReturnGenreNameTest() {
        genre.setId(1L);
        genreDbStorage.update(genre);

        assertThat(genreDbStorage.getById(1L))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Тестовый жанр");
    }

    @Test
    void shouldReturnCollectionSize5() {
        genre.setId(1L);
        genre.setName("Комедия");
        genreDbStorage.delete(genre);

        assertThat(genreDbStorage.getAll()).hasSize(5);
    }

    @Test
    void shouldReturnTrueWhenContainsGenre() {
        assertTrue(genreDbStorage.contains(1L));
    }

    @Test
    void shouldReturnFalseWhenContainsNoGenre() {
        assertFalse(genreDbStorage.contains(10L));
    }

    @Test
    void shouldReturnCollectionSize6() {
        assertThat(genreDbStorage.getAll()).hasSize(6);
    }
}
