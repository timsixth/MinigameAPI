package pl.timsixth.minigameapi.api.stats.model;

import lombok.Getter;
import pl.timsixth.minigameapi.api.model.annotations.Id;

import java.util.UUID;

@Getter
public abstract class AbstractUserStats implements UserStats {

    @Id
    private final UUID uuid;
    private final String name;
    private final String arenaName;
    private int wins;
    private int defeats;

    public AbstractUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        this.uuid = uuid;
        this.name = name;
        this.arenaName = arenaName;
        this.wins = wins;
        this.defeats = defeats;
    }

    @Override
    public void addWin() {
        wins++;
        update();
    }

    @Override
    public void addDefeat() {
        defeats++;
        update();
    }

    @Override
    public int getGamesPlayed() {
        return wins + defeats;
    }
}
