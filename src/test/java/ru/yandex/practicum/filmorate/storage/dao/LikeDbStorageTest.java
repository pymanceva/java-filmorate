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
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LikeDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final LikeDbStorage likeDbStorage;
    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("Фильм");
        film.setDuration(90);
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setMpa(new MpaRating(1L, null));
        filmDbStorage.add(film);

        User user1 = new User();
        user1.setId(1);
        user1.setEmail("1@ya.ru");
        user1.setLogin("1");
        userDbStorage.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("2@ya.ru");
        user2.setLogin("2");
        userDbStorage.add(user2);

        User user3 = new User();
        user3.setId(3);
        user3.setEmail("3@ya.ru");
        user3.setLogin("3");
        userDbStorage.add(user3);
    }

    @Test
    void shouldReturnCollectionSize1() {
        likeDbStorage.add(1L, 1L);

        assertThat(likeDbStorage.getAll()).hasSize(1);
    }

    @Test
    void shouldReturnCollectionSize0() {
        likeDbStorage.add(1L, 1L);
        likeDbStorage.delete(1L, 1L);

        assertThat(likeDbStorage.getAll()).hasSize(0);
    }

    @Test
    void shouldReturnFalseWhenContainsNoLike() {
        assertFalse(likeDbStorage.contains(1L, 1L));
    }

    @Test
    void shouldReturnFilm1() {
        Film filmPopular = new Film();
        filmPopular.setName("Фильм топ");
        filmPopular.setDuration(90);
        filmPopular.setDescription("Описание топ");
        filmPopular.setReleaseDate(LocalDate.of(2000, 01, 01));
        filmPopular.setMpa(new MpaRating(1L, null));
        filmDbStorage.add(filmPopular);
        likeDbStorage.add(1L, 1L);
        likeDbStorage.add(2L, 1L);
        likeDbStorage.add(2L, 2L);
        likeDbStorage.add(2L, 3L);

        Collection<Film> films = new ArrayList<>(likeDbStorage.getMostPopularFilmsByLikes(1));
        assertThat(films)
                .hasSize(1)
                .extracting(Film::getName)
                .containsExactly("Фильм топ");
    }

    @Test
    void shouldReturnCollectionSize3() {
        likeDbStorage.add(1L, 1L);
        likeDbStorage.add(1L, 2L);
        likeDbStorage.add(1L, 3L);

        assertThat(likeDbStorage.getLikes(film.getId())).hasSize(3);
    }
}
