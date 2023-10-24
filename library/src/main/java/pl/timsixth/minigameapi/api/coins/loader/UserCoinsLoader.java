package pl.timsixth.minigameapi.api.coins.loader;

import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.UserCoinsImpl;
import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @see AbstractSqlDataBaseLoader
 */
public class UserCoinsLoader extends AbstractSqlDataBaseLoader<UserCoins> {

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
                UserCoins userStats = new UserCoinsImpl(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getInt("coins")
                );

                getData().add(userStats);
            }
        } catch (ExecutionException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getTableName() {
        return UserCoinsImpl.TABLE_NAME;
    }
}
