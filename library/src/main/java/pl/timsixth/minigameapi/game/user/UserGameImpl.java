package pl.timsixth.minigameapi.game.user;

import lombok.*;

import java.util.UUID;

/**
 * Implementation of {@link UserGame}
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class UserGameImpl implements UserGame {

    private final UUID uuid;
    private int points;
    private boolean isPlaying;

    @Override
    public int addPoints(int points) {
        points++;
        return points;
    }

    @Override
    public int removePoints(int points) {
        points--;
        return points;
    }
}
