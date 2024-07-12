package pl.timsixth.minigameapi.api.module.sql.stats;

import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.sql.core.dao.SQLDatabaseDao;

import java.util.LinkedHashMap;
import java.util.Map;

public class SQLDatabaseUserStatsDao extends SQLDatabaseDao {

    private final SQLDatabaseUserStats userStats;

    public SQLDatabaseUserStatsDao(Model model) {
        super(model);

        this.userStats = (SQLDatabaseUserStats) model;
    }

    @Override
    public Object update(Model model) {

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("wins", userStats.getWins());
        data.put("defeats", userStats.getDefeats());

        getSqlDatabaseAdapter().updateWhere(userStats.getTableNameWithPrefix(), data,
                "uuid = '" + userStats.getUuid() + "' AND arenaName = '" + userStats.getArenaName() + "'");

        return model;
    }

    @Override
    public boolean delete(Model model) {
        getSqlDatabaseAdapter().deleteAllWhere(userStats.getTableNameWithPrefix(),
                "uuid = '" + userStats.getUuid() + "' AND arenaName = '" + userStats.getArenaName() + "'");

        return true;
    }
}
