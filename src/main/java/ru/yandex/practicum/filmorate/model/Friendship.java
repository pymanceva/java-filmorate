package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Friendship {
    @NonNull
    private Long userId;
    @NonNull
    private Long friendId;
}
