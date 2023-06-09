package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FriendshipDbStorageTest {

    private final FriendshipDbStorage friendshipDBStorage;
    private final UserDbStorage userDbStorage;

    @BeforeEach
    void setUp() {
        Friendship friendship = new Friendship();
        friendship.setUserId(1L);
        friendship.setFriendId(2L);

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
    void shouldReturnFriendshipBetweenId1And2() {
        friendshipDBStorage.add(1L, 2L);

        assertThat(friendshipDBStorage.getById(1L, 2L))
                .hasFieldOrPropertyWithValue("userId", 1L)
                .hasFieldOrPropertyWithValue("friendId", 2L);
    }

    @Test
    void shouldReturnCollectionSize0() {
        friendshipDBStorage.add(1L, 2L);
        friendshipDBStorage.delete(1L, 2L);

        assertThat(friendshipDBStorage.getAllFriendsOfUser(1L)).hasSize(0);
    }

    @Test
    void shouldReturnCollectionSize2() {
        friendshipDBStorage.add(1L, 2L);
        friendshipDBStorage.add(1L, 3L);
        assertThat(friendshipDBStorage.getAllFriendsOfUser(1L)).hasSize(2);
    }

    @Test
    void shouldReturnCollectionSize1() {
        friendshipDBStorage.add(1L, 3L);
        friendshipDBStorage.add(1L, 2L);
        friendshipDBStorage.add(3L, 2L);
        friendshipDBStorage.add(3L, 1L);
        assertThat(friendshipDBStorage.getCommonFriends(1L, 3L)).hasSize(1);
    }

    @Test
    void shouldReturnTrueWhenContainsFriendship() {
        friendshipDBStorage.add(1L, 2L);

        assertTrue(friendshipDBStorage.contains(1L, 2L));
    }
}
