package pl.timsixth.minigameapi.api.stats.model;

import lombok.Getter;
import pl.timsixth.minigameapi.api.database.annoations.Table;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.UUID;

/**
 * Adapter for UserStats model which is saving in SQL database
 *
 * @see Table
 * @see AbstractUserStatsAdapter
 * @see UserStats
 * @see AbstractModelAdapter
 * @deprecated use SQL module
 */
@Table(name = "users_stats")
@Getter
@Deprecated
public class SQLDatabaseUserStatsAdapter extends AbstractUserStatsAdapter {

    public static final String TABLE_NAME = "users_stats";

    public SQLDatabaseUserStatsAdapter(InitializableModel model, UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(model, uuid, name, arenaName, wins, defeats);
        model.init(this);
    }

    @Override
    public Object update() {
        throw new UnsupportedOperationException("use SQL module");
    }

    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("use SQL module");
    }
}
