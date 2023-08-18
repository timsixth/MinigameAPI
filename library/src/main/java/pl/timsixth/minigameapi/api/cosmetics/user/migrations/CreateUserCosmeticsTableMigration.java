package pl.timsixth.minigameapi.api.cosmetics.user.migrations;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.ISQLite;
import pl.timsixth.databasesapi.database.migration.ICreationMigration;
import pl.timsixth.databasesapi.database.structure.DataType;
import pl.timsixth.minigameapi.api.MiniGame;

import java.sql.SQLException;

/**
 * Creates table users_cosmetics in database
 * More information in T-DataBasesAPI on my GitHub
 */
public class CreateUserCosmeticsTableMigration implements ICreationMigration {

    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

    /**
     * @return table name
     */
    @Override
    public String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_cosmetics";
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
     * @throws SQLException when can not execute query
     */
    @Override
    public void up() throws SQLException {
        if (currentSqlDataBase instanceof ISQLite) {
            createTableUserCoins(DataType.INTEGER);
        } else {
            createTableUserCoins(DataType.INT);
        }
    }

    /**
     * Creates table in database
     *
     * @param idDataType type of id in table
     * @throws SQLException when can not execute query
     */
    private void createTableUserCoins(DataType idDataType) throws SQLException {
        currentSqlDataBase.getTableCreator()
                .createColumn("id", idDataType, 11, false)
                .primaryKey("id", true)
                .autoIncrement("id", true)
                .createColumn("uuid", DataType.VARCHAR, 36, false)
                .createColumn("cosmetic", DataType.VARCHAR, 50, false)
                .createColumn("enabled", DataType.BOOLEAN, 1, false)
                .defaultValue("enabled", false)
                .create(getTableName());
    }
}