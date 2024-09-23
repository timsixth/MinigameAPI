package pl.timsixth.minigameapi.api.module.sql.core;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.ManageableModel;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.sql.core.annotations.Table;
import pl.timsixth.minigameapi.api.module.sql.core.dao.SQLDatabaseDao;
import pl.timsixth.minigameapi.api.storage.Dao;

/**
 * Represents every database model.
 *
 * @see Model
 */
public interface DbModel extends ManageableModel {

    /**
     * @return table name
     */
    default String getTableName() {
        String tableName = "";

        Class<?> clazz = this.getClass();

        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);

            tableName = table.name();
        }

        return tableName;
    }

    /**
     * Gets table name with plugin's table prefix
     *
     * @return table name with plugin's table prefix
     */
    default String getTableNameWithPrefix() {
        String tableName = getTableName();

        if (tableName.isEmpty()) {
            throw new IllegalStateException("Cannot get table name. Add @Table annotation or overwrite getTableName method");
        }

        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + getTableName();
    }

    @Override
    default Dao getDao() {
        return new SQLDatabaseDao(this);
    }
}
