package pl.timsixth.minigameapi.api.game.user;

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
        return this.points += points;
    }

    @Override
    public int removePoints(int points) {
        return this.points -= points;
    }

    @Override
    public boolean hasPoints(int points) {
        return this.points >= points;
    }
}
