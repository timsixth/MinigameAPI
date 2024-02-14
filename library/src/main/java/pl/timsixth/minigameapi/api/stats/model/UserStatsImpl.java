package pl.timsixth.minigameapi.api.stats.model;

import lombok.Getter;
import lombok.ToString;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;

import java.util.UUID;

/**
 * Represents default user stats
 *
 * @see UserStats
 * @see AbstractDbModel
 */
@Getter
@ToString
public class UserStatsImpl extends AbstractDbModel implements UserStats {

    @Id
    private final UUID uuid;
    private final String name;
    private final String arenaName;
    private int wins;
    private int defeats;

    public static final String TABLE_NAME = "users_stats";

    public UserStatsImpl(UUID uuid, String name, String arenaName) {
        this.uuid = uuid;
        this.name = name;
        this.arenaName = arenaName;
        init();
    }

    public UserStatsImpl(UUID uuid, String name, String arenaName, int wins, int defeats) {
        this.uuid = uuid;
        this.name = name;
        this.arenaName = arenaName;
        this.wins = wins;
        this.defeats = defeats;
        init();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
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
