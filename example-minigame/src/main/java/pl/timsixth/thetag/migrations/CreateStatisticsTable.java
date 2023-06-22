package pl.timsixth.thetag.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.ISQLite;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.DataType;

import java.sql.SQLException;

public class CreateStatisticsTable implements ICreationMigration {

    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public String getTableName() {
        return "thetag_stats";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void up() throws SQLException {
        if (currentSqlDataBase instanceof ISQLite) {
            createTableUserStats(DataType.INTEGER);
        } else {
            createTableUserStats(DataType.INT);
        }
    }

    private void createTableUserStats(DataType idDataType) throws SQLException {
        currentSqlDataBase.getTableCreator()
                .createColumn("id", idDataType, 11, false)
                .primaryKey("id", true)
                .autoIncrement("id", true)
                .createColumn("uuid", DataType.VARCHAR, 36, false)
                .createColumn("name", DataType.VARCHAR, 30, false)
                .createColumn("arenaName", DataType.VARCHAR, 50, false)
                .createColumn("wins", DataType.INT, 11, false)
                .defaultValue("wins", 0)
                .createColumn("defeats", DataType.INT, 11, false)
                .defaultValue("defeats", 0)
                .createColumn("gamesPlayed", DataType.INT, 11, false)
                .defaultValue("gamesPlayed", 0)
                .create(getTableName());
    }
}
