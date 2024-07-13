package pl.timsixth.minigameapi.api.module.sql.core.integration;

import java.sql.ResultSet;
import java.util.Map;

/**
 * The adapter for SQL database queries, use this to implement your own logic to SQL databases
 */
public interface SQLDatabaseAdapter {

    /**
     * Executes update async
     *
     * @param sql the query
     */
    void executeUpdateAsync(String sql);

    /**
     * Executes query async
     *
     * @param query the query which will be executed
     * @return result set of query
     */
    ResultSet executeQueryAsync(String query);

    /**
     * Inserts data to database
     *
     * @param tableName the table name
     * @param data      varargs of data which will be inserted to database
     */
    void insert(String tableName, Object... data);

    /**
     * Deletes all from table when the where condition is pass, this is not truncate action
     *
     * @param tableName      the table name
     * @param whereCondition where condition
     */
    void deleteAllWhere(String tableName, String whereCondition);

    /**
     * Updates some fields provided in map when the where condition is pass
     *
     * @param tableName      the table name
     * @param data           map of data to update
     * @param whereCondition where condition
     */
    void updateWhere(String tableName, Map<String, Object> data, String whereCondition);

    /**
     * Selects all data from table
     *
     * @param tableName the table name
     * @return result set of query
     */
    ResultSet selectAll(String tableName);

    /**
     * Selects all data from table when the where condition is pass
     *
     * @param tableName      the table name
     * @param whereCondition where condition
     * @param columnNames    columns to queried
     * @return result set of query
     */
    ResultSet selectWhere(String tableName, String whereCondition, String... columnNames);
}
