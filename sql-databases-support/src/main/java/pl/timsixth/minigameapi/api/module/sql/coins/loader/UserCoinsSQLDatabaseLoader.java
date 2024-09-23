package pl.timsixth.minigameapi.api.module.sql.coins.loader;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.logging.MiniGameLogger;
import pl.timsixth.minigameapi.api.module.sql.coins.SQLDatabaseUserCoins;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.core.loader.AbstractSqlDataBaseLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @see AbstractSqlDataBaseLoader
 */
public class UserCoinsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserCoins> implements UserCoinsLoader {

    public UserCoinsSQLDatabaseLoader(SQLDatabaseAdapter sqlDatabaseAdapter) {
        super(sqlDatabaseAdapter);
    }

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
        try (ResultSet resultSet = sqlDatabaseAdapter.selectAll(getTableNameWithPrefix())) {
            while (resultSet.next()) {
                UserCoins userStats = MiniGame.getUserCoinsFactory().createUserCoins(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getInt("coins")
                );

                getData().add(userStats);
            }
        } catch (SQLException e) {
            MiniGameLogger.error(e.getMessage(), e);
        }
    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserCoins.TABLE_NAME;
    }
}
