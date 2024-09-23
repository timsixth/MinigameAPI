package pl.timsixth.minigameapi.api.module.sql.stats.loader;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.logging.MiniGameLogger;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.core.loader.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.api.module.sql.stats.SQLDatabaseUserStats;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Loads stats from database
 */
public class UserStatsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserStats> implements UserStatsLoader {

    public UserStatsSQLDatabaseLoader(SQLDatabaseAdapter sqlDatabaseAdapter) {
        super(sqlDatabaseAdapter);
    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserStats.TABLE_NAME;
    }

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        try (ResultSet resultSet = sqlDatabaseAdapter.selectAll(tableName)) {

            while (resultSet.next()) {
                UserStats userStats = MiniGame.getUserStatsFactory().createUserStats(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("arenaName"),
                        resultSet.getInt("wins"),
                        resultSet.getInt("defeats")
                );

                getData().add(userStats);
            }

        } catch (SQLException e) {
            MiniGameLogger.error(e.getMessage(), e);
        }
    }
}
