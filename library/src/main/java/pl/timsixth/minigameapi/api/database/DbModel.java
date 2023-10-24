package pl.timsixth.minigameapi.api.database;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Represents every database model.
 *
 * @see DbModel
 */
public interface DbModel extends Model {
    /**
     * @return table name
     */
    String getTableName();

    default String getTableNameWithPrefix() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + getTableName();
    }
}
