package pl.timsixth.minigameapi.api.stats.model;

import lombok.Getter;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.DbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;

import java.util.UUID;

/**
 * Represents default user stats
 *
 * @see UserStats
 * @see AbstractDbModel
 */
@Getter
public class UserStatsImpl extends AbstractDbModel implements UserStats, DbModel {

    @Id
    private final UUID uuid;
    private final String name;
    private final String arenaName;
    private int wins;
    private int defeats;

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
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_stats";
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
