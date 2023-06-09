package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("Фильм");
        film.setDuration(90);
        film.setDescription("Описание");
        film.setReleaseDate(Date.valueOf("1896-01-02"));
        film.setMpa(new MpaRating(1L, null));
    }

    @Test
    void shouldReturnFilmId1() {
        filmDbStorage.add(film);

        assertThat(filmDbStorage.getById(1L))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Фильм")
                .hasFieldOrPropertyWithValue("description", "Описание")
                .hasFieldOrPropertyWithValue("duration", 90);

        assertThat(filmDbStorage.getById(1L).getMpa())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "G");
    }

    @Test
    void shouldReturnFilmNameUpdated() {
        filmDbStorage.add(film);
        film.setName("updated");
        filmDbStorage.update(film);

        assertThat(filmDbStorage.getById(1L))
                .hasFieldOrPropertyWithValue("name", "updated");
    }

    @Test
    void shouldReturnCollectionSize0() {
        filmDbStorage.add(film);
        filmDbStorage.delete(film);

        assertThat(filmDbStorage.getAll()).hasSize(0);
    }

    @Test
    void shouldReturnCollectionSize1() {
        filmDbStorage.add(film);

        assertThat(filmDbStorage.getAll()).hasSize(1);
    }

    @Test
    void shouldReturnTrueWhenContainsFilm() {
        filmDbStorage.add(film);

        assertTrue(filmDbStorage.contains(1L));
    }

    @Test
    void shouldReturnFalseWhenContainsNoFilm() {
        assertFalse(filmDbStorage.contains(1L));
    }
}
