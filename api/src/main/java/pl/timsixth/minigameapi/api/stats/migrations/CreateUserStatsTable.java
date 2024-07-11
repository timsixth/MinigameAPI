package pl.timsixth.minigameapi.api.stats.migrations;

/**
 * Creates table users_stats in database
 * More information in T-DataBasesAPI on my GitHub
 */
@Deprecated
public class CreateUserStatsTable {

//    private final ISQLDataBase currentSqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
//
//    @Override
//    public final String getTableName() {
//        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + "users_stats";
//    }
//
//    @Override
//    public final int getVersion() {
//        return 1;
//    }
//
//    @Override
//    public void up() throws SQLException {
//        createTableUserStats().createTable(getTableName());
//    }
//
//    protected ITable createTableUserStats() {
//        return currentSqlDataBase.getTableCreator()
//                .id()
//                .createColumn("uuid", new VarcharDataType(36), false)
//                .createColumn("name", new VarcharDataType(30), false)
//                .createColumn("arenaName", new VarcharDataType(50), false)
//                .createColumn("wins", DataTypes.INT, false)
//                .defaultValue("wins", 0)
//                .createColumn("defeats", DataTypes.INT, false)
//                .defaultValue("defeats", 0);
//    }
}
