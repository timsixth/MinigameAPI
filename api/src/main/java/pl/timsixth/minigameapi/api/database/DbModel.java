package pl.timsixth.minigameapi.api.database;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Represents every database model.
 *
 * @see Model
 * @deprecated use SQL module
 */
@Deprecated
public interface DbModel extends Model {
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
