package pl.timsixth.minigameapi.api.module.sql.coins.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.datatype.DataTypes;
import pl.timsixth.databasesapi.database.structure.datatype.VarcharDataType;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.module.sql.core.integration.migrator.T_DatabaseApiMigration;

/**
 * Creates table user_coins in database
 * More information in T-DataBasesAPI on my GitHub
 */
public class CreateUserCoinsTableMigration implements ICreationMigration, T_DatabaseApiMigration {
    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    /**
     * @return table name
     */
    @Override
    public String getTableName() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + "users_coins";
    }

    /**
     * @return version of migration
     */
    @Override
    public int getVersion() {
        return 1;
    }

    /**
     * Creates table in sqlite or mysql
     *
     */
    @Override
    public void up() {
        currentSqlDataBase.getTableCreator()
                .id()
                .createColumn("uuid", new VarcharDataType(36), false)
                .createColumn("coins", DataTypes.DOUBLE, false)
                .defaultValue("coins", 0)
                .createTable(getTableName());
    }
}
