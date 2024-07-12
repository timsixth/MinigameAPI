package pl.timsixth.minigameapi.api.module.sql.core.integration;

import java.sql.ResultSet;
import java.util.Map;

public interface SQLDatabaseAdapter {

    void executeUpdateAsync(String sql);

    ResultSet executeQueryAsync(String query);

    void insert(String tableName, Object... data);

    void deleteAll(String tableName);

    void deleteAllWhere(String tableName, String whereCondition);

    void updateWhere(String tableName, Map<String, Object> data, String whereCondition);

    ResultSet selectAll(String tableName);

    ResultSet selectWhere(String tableName, String whereCondition, String... columnNames);
}
