package pl.timsixth.minigameapi.api.stats.loader;

import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.stats.model.UserStatsImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Loads stats from database
 */
public class UserStatsLoader extends AbstractSqlDataBaseLoader<UserStats> {

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        try (ResultSet resultSet = currentSqlDataBase.getAsyncQuery().query(query)) {

            while (resultSet.next()) {
                UserStats userStats = new UserStatsImpl(
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

    @Override
    protected String getTableName() {
        return UserStatsImpl.TABLE_NAME;
    }
}
