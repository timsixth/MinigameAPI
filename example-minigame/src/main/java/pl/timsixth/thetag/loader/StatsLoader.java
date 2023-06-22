package pl.timsixth.thetag.loader;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.loader.database.AbstractSqlDataBaseLoader;
import pl.timsixth.thetag.model.UserStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class StatsLoader extends AbstractSqlDataBaseLoader<UserStats> {

    private final ISQLDataBase sqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public void load() {
        load("thetag_stats");
    }

    @Override
    public void load(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        try (ResultSet resultSet = sqlDataBase.getAsyncQuery().query(query)) {

            while (resultSet.next()) {
                UserStats userStats = new UserStats(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("arenaName"),
                        resultSet.getInt("wins"),
                        resultSet.getInt("defeats"),
                        resultSet.getInt("gamesPlayed")
                );

                getData().add(userStats);
            }

        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
