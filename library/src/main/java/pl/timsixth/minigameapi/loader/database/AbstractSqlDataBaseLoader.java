package pl.timsixth.minigameapi.loader.database;

import pl.timsixth.minigameapi.database.DbModel;
import pl.timsixth.minigameapi.loader.AbstractLoader;

public abstract class AbstractSqlDataBaseLoader<T extends DbModel> extends AbstractLoader<T> implements SqlDataBaseLoader<T> {
}
