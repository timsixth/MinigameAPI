package pl.timsixth.minigameapi.loader.database;

import pl.timsixth.minigameapi.database.DbModel;
import pl.timsixth.minigameapi.loader.Loader;

public interface SqlDataBaseLoader<T extends DbModel> extends Loader<T> {

    void load(String tableName);
}
