package pl.timsixth.minigameapi.stats.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.ISQLite;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.DataType;
import pl.timsixth.databasesapi.database.structure.ITable;
import pl.timsixth.minigameapi.MiniGame;

import java.sql.SQLException;
/**
 * Creates table users_stats in database
 * More information in T-DataBasesAPI on my GitHub
 */
public class CreateUserStatsTable implements ICreationMigration {

    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public final String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_stats";
    }

    @Override
    public final int getVersion() {
        return 1;
    }

    @Override
    public final void up() throws SQLException {
        if (currentSqlDataBase instanceof ISQLite) {
            createTableUserStats(DataType.INTEGER).create(getTableName());
        } else {
            createTableUserStats(DataType.INT).create(getTableName());
        }
    }

    protected ITable createTableUserStats(DataType idDataType) {
        return currentSqlDataBase.getTableCreator()
                .createColumn("id", idDataType, 11, false)
                .primaryKey("id", true)
                .autoIncrement("id", true)
                .createColumn("uuid", DataType.VARCHAR, 36, false)
                .createColumn("name", DataType.VARCHAR, 30, false)
                .createColumn("arenaName", DataType.VARCHAR, 50, false)
                .createColumn("wins", DataType.INT, 11, false)
                .defaultValue("wins", 0)
                .createColumn("defeats", DataType.INT, 11, false)
                .defaultValue("defeats", 0);
    }
}
