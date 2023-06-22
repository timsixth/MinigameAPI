package pl.timsixth.minigameapi.coins.loader;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.UserCoinsImpl;
import pl.timsixth.minigameapi.loader.database.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @see AbstractSqlDataBaseLoader
 * @see UserCoinsDbModel
 */
public class UserCoinsLoader extends AbstractSqlDataBaseLoader<UserCoinsDbModel> {
    /**
     * Loads data from table
     */
    @Override
    public void load() {
        load(MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_coins");
    }

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        try (ResultSet resultSet = currentSqlDataBase.getAsyncQuery().query(query)) {
            while (resultSet.next()) {
                UserCoinsDbModel userStats = new UserCoinsImpl(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getInt("coins")
                );

                getData().add(userStats);
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
