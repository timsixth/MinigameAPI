package pl.timsixth.minigameapi.game.user;

import lombok.*;
import org.bukkit.GameMode;

import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class UserGameImpl implements UserGame {

    private final UUID uuid;
    private int points;
    private GameMode gameMode;
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

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
