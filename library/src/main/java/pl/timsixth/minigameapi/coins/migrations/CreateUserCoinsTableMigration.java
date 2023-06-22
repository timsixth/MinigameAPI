package pl.timsixth.minigameapi.coins.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.ISQLite;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.DataType;
import pl.timsixth.minigameapi.MiniGame;

import java.sql.SQLException;

public class CreateUserCoinsTableMigration implements ICreationMigration {
    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() +"users_coins";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void up() throws SQLException {
        if (currentSqlDataBase instanceof ISQLite) {
            createTableUserCoins(DataType.INTEGER);
        } else {
            createTableUserCoins(DataType.INT);
        }
    }

    private void createTableUserCoins(DataType idDataType) throws SQLException {
        currentSqlDataBase.getTableCreator()
                .createColumn("id", idDataType, 11, false)
                .primaryKey("id", true)
                .autoIncrement("id", true)
                .createColumn("uuid", DataType.VARCHAR, 36, false)
                .createColumn("coins", DataType.INT, 11, false)
                .defaultValue("coins", 0)
                .create(getTableName());
    }
}
