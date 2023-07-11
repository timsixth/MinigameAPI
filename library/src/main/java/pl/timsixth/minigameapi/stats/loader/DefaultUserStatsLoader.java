package pl.timsixth.minigameapi.stats.loader;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.loader.database.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.stats.model.DefaultUserStats;
import pl.timsixth.minigameapi.stats.model.UserStatsDbModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Loads stats from database
 */
public class DefaultUserStatsLoader extends AbstractSqlDataBaseLoader<UserStatsDbModel> {

    private final ISQLDataBase sqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
    /**
     * Loads data from table
     */
    @Override
    public void load() {
        load(MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_stats");
    }
    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        try (ResultSet resultSet = sqlDataBase.getAsyncQuery().query(query)) {

            while (resultSet.next()) {
                UserStatsDbModel userStats = new DefaultUserStats(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("arenaName"),
                        resultSet.getInt("wins"),
                        resultSet.getInt("defeats")
                );

                getData().add(userStats);
            }

        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
