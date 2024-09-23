package pl.timsixth.minigameapi.api.module.sql.stats;

import lombok.Getter;
import pl.timsixth.minigameapi.api.module.sql.core.DbModel;
import pl.timsixth.minigameapi.api.module.sql.core.annotations.Table;
import pl.timsixth.minigameapi.api.stats.model.AbstractUserStats;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

@Table(name = "users_stats")
@Getter
public class SQLDatabaseUserStats extends AbstractUserStats implements DbModel {

    public static final String TABLE_NAME = "users_stats";

    public SQLDatabaseUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(uuid, name, arenaName, wins, defeats);
    }

    @Override
    public Dao getDao() {
        return new SQLDatabaseUserStatsDao(this);
    }
}
