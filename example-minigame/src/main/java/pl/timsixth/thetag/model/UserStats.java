package pl.timsixth.thetag.model;

import lombok.Getter;
import pl.timsixth.minigameapi.database.AbstractDbModel;
import pl.timsixth.minigameapi.database.annoations.Id;
import pl.timsixth.minigameapi.user.User;

import java.util.UUID;

@Getter
public class UserStats extends AbstractDbModel implements User {

    @Id
    private final UUID uuid;
    private final String name;
    private final String arenaName;
    private int wins;
    private int defeats;
    private int gamesPlayed;

    public UserStats(UUID uuid, String name, String arenaName) {
        this.uuid = uuid;
        this.name = name;
        this.arenaName = arenaName;
        init();
    }

    public UserStats(UUID uuid, String name, String arenaName, int wins, int defeats, int gamesPlayed) {
        this.uuid = uuid;
        this.name = name;
        this.arenaName = arenaName;
        this.wins = wins;
        this.defeats = defeats;
        this.gamesPlayed = gamesPlayed;
        init();
    }

    @Override
    public String getTableName() {
        return "thetag_stats";
    }

    public void addWin() {
        wins++;
        update();
    }

    public void addDefeat() {
        defeats++;
        update();
    }

    public void addGamePlayed() {
        gamesPlayed++;
        update();
    }
}
