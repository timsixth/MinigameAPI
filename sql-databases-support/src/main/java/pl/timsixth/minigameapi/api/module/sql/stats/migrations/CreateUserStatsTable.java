package pl.timsixth.minigameapi.api.module.sql.stats.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.ITable;
import pl.timsixth.databasesapi.database.structure.datatype.DataTypes;
import pl.timsixth.databasesapi.database.structure.datatype.VarcharDataType;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.module.sql.core.integration.migrator.T_DatabaseApiMigration;

/**
 * Creates table users_stats in database
 * More information in T-DataBasesAPI on my GitHub
 */
public class CreateUserStatsTable implements ICreationMigration, T_DatabaseApiMigration {

    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    @Override
    public final String getTableName() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + "users_stats";
    }

    @Override
    public final int getVersion() {
        return 1;
    }

    @Override
    public void up() {
        createTableUserStats().createTable(getTableName());
    }

    protected ITable createTableUserStats() {
        return currentSqlDataBase.getTableCreator()
                .id()
                .createColumn("uuid", new VarcharDataType(36), false)
                .createColumn("name", new VarcharDataType(30), false)
                .createColumn("arenaName", new VarcharDataType(50), false)
                .createColumn("wins", DataTypes.INT, false)
                .defaultValue("wins", 0)
                .createColumn("defeats", DataTypes.INT, false)
                .defaultValue("defeats", 0);
    }
}
