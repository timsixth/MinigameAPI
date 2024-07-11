package pl.timsixth.minigameapi.api.stats.loader;

import pl.timsixth.minigameapi.api.loader.database.AbstractSqlDataBaseLoader;
import pl.timsixth.minigameapi.api.stats.model.SQLDatabaseUserStatsAdapter;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

/**
 * Loads stats from database
 */
@Deprecated
public class UserStatsSQLDatabaseLoader extends AbstractSqlDataBaseLoader<UserStats> implements UserStatsLoader {

    /**
     * Loads data from database
     *
     * @param tableName table with data to load
     */
    @Override
    public void load(String tableName) {
//        QueryBuilder queryBuilder = new QueryBuilder();
//
//        String query = queryBuilder.selectAll(tableName).build();
//
//        try (ResultSet resultSet = currentSqlDataBase.getAsyncQuery().query(query)) {
//
//            while (resultSet.next()) {
//                UserStats userStats = MiniGame.getUserStatsFactory().createUserStats(
//                        UUID.fromString(resultSet.getString("uuid")),
//                        resultSet.getString("name"),
//                        resultSet.getString("arenaName"),
//                        resultSet.getInt("wins"),
//                        resultSet.getInt("defeats")
//                );
//
//                getData().add(userStats);
//            }
//
//        } catch (ExecutionException | InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    protected String getTableName() {
        return SQLDatabaseUserStatsAdapter.TABLE_NAME;
    }
}
