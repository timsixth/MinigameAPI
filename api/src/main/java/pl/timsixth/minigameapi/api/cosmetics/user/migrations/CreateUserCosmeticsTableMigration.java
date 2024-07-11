package pl.timsixth.minigameapi.api.cosmetics.user.migrations;

/**
 * Creates table users_cosmetics in database
 * More information in T-DataBasesAPI on my GitHub
 */
@Deprecated
public class CreateUserCosmeticsTableMigration {
//
//    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
//
//    /**
//     * @return table name
//     */
//    @Override
//    public String getTableName() {
//        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + "users_cosmetics";
//    }
//
//    /**
//     * @return version of migration
//     */
//    @Override
//    public int getVersion() {
//        return 1;
//    }
//
//    /**
//     * Creates table in sqlite or mysql
//     *
//     * @throws SQLException when can not execute query
//     */
//    @Override
//    public void up() throws SQLException {
//        currentSqlDataBase.getTableCreator()
//                .id()
//                .createColumn("uuid", new VarcharDataType(36), false)
//                .createColumn("cosmetic", new VarcharDataType(50), false)
//                .createColumn("enabled", DataTypes.BOOLEAN, false)
//                .defaultValue("enabled", false)
//                .createTable(getTableName());
//    }
}
