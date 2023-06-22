package pl.timsixth.minigameapi.database;

import pl.timsixth.minigameapi.model.Model;

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
}
