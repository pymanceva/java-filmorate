package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDbStorageTest {

    private final UserDbStorage userDbStorage;
    private User user1;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setEmail("1@ya.ru");
        user1.setLogin("1");
    }

    @Test
    void shouldReturnUserId1() {
        userDbStorage.add(user1);

        assertThat(userDbStorage.getById(1L))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("login", "1")
                .hasFieldOrPropertyWithValue("email", "1@ya.ru");
    }

    @Test
    void shouldReturnUserNameUpdated() {
        userDbStorage.add(user1);
        user1.setLogin("updated");
        userDbStorage.update(user1);

        assertThat(userDbStorage.getById(1L))
                .hasFieldOrPropertyWithValue("login", "updated");
    }

    @Test
    void shouldReturnCollectionSize0() {
        userDbStorage.add(user1);
        userDbStorage.delete(user1);

        assertThat(userDbStorage.getAll()).hasSize(0);
    }

    @Test
    void shouldReturnCollectionSize1() {
        userDbStorage.add(user1);

        assertThat(userDbStorage.getAll()).hasSize(1);
    }

    @Test
    void shouldReturnTrueWhenContainsUser() {
        userDbStorage.add(user1);

        assertTrue(userDbStorage.contains(1L));
    }

    @Test
    void shouldReturnFalseWhenContainsNoUser() {
        assertFalse(userDbStorage.contains(1L));
    }
}
