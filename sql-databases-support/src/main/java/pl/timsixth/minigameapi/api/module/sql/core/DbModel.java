package pl.timsixth.minigameapi.api.module.sql.core;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.ManageableModel;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Represents every database model.
 *
 * @see Model
 */
public interface DbModel extends ManageableModel {

    /**
     * @return table name
     */
    String getTableName();

    /**
     * Gets table name with plugin's table prefix
     *
     * @return table name with plugin's table prefix
     */
    default String getTableNameWithPrefix() {
        return MiniGame.getInstance().getPluginConfiguration().getTablesPrefix() + getTableName();
    }
}
