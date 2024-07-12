package pl.timsixth.minigameapi.api.module.sql.core.integration;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.MiniGame;

import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class T_DataBaseApiIntegration implements SQLDatabaseAdapter {

    private final ISQLDataBase sqlDataBase;

    public T_DataBaseApiIntegration() {
        this.sqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
    }

    @Override
    public void executeUpdateAsync(String query) {
        try {
            sqlDataBase.getAsyncQuery().update(query);
        } catch (ExecutionException | InterruptedException e) {
            MiniGame.getInstance().getLogger().log(Level.SEVERE, "Can not execute update async", e);
        }
    }

    @Override
    public ResultSet executeQueryAsync(String query) {
        try {
            return sqlDataBase.getAsyncQuery().query(query);
        } catch (ExecutionException | InterruptedException e) {
            MiniGame.getInstance().getLogger().log(Level.SEVERE, "Can not execute query async", e);
        }

        return null;
    }

    @Override
    public void insert(String tableName, Object... data) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.insert(tableName, data).build();

        executeUpdateAsync(query);
    }

    @Override
    public void deleteAll(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(tableName).build();

        executeUpdateAsync(query);
    }

    @Override
    public void deleteAllWhere(String tableName, String whereCondition) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(tableName)
                .where(whereCondition)
                .build();

        executeUpdateAsync(query);
    }

    @Override
    public void updateWhere(String tableName, Map<String, Object> data, String whereCondition) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.update(tableName, data)
                .where(whereCondition)
                .build();

        executeUpdateAsync(query);
    }

    @Override
    public ResultSet selectAll(String tableName) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.selectAll(tableName).build();

        return executeQueryAsync(query);
    }

    @Override
    public ResultSet selectWhere(String tableName, String whereCondition, String... columnNames) {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.select(tableName, columnNames)
                .where(whereCondition)
                .build();

        return executeQueryAsync(query);
    }
}
