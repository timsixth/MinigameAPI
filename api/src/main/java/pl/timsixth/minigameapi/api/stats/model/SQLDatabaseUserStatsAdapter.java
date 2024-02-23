package pl.timsixth.minigameapi.api.stats.model;

import lombok.Getter;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Table;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Adapter for UserStats model which is saving in SQL database
 *
 * @see Table
 * @see AbstractUserStatsAdapter
 * @see UserStats
 * @see AbstractModelAdapter
 */
@Table(name = "users_stats")
@Getter
public class SQLDatabaseUserStatsAdapter extends AbstractUserStatsAdapter {

    public static final String TABLE_NAME = "users_stats";

    private final ISQLDataBase database;
    private final AbstractDbModel abstractDbModel;

    public SQLDatabaseUserStatsAdapter(InitializableModel model, UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(model, uuid, name, arenaName, wins, defeats);
        model.init(this);

        database = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();
        abstractDbModel = (AbstractDbModel) model;
    }

    @Override
    public Object update() {
        QueryBuilder queryBuilder = new QueryBuilder();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("wins", getWins());
        data.put("defeats", getDefeats());

        String query = queryBuilder.update(abstractDbModel.getTableNameWithPrefix(), data)
                .where("uuid = '" + getUuid() + "' AND arenaName = '" + getArenaName() + "'")
                .build();

        abstractDbModel.executeUpdate(query);

        return model;
    }

    @Override
    public boolean delete() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(abstractDbModel.getTableNameWithPrefix())
                .where("uuid = '" + getUuid() + "' AND arenaName = '" + getArenaName() + "'")
                .build();

        abstractDbModel.executeUpdate(query);

        return true;
    }
}
