package pl.timsixth.minigameapi.api.module.sql.cosmetics;

import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.sql.core.dao.SQLDatabaseDao;

import java.util.HashMap;
import java.util.Map;

public class SQLDatabaseUserCosmeticsDao extends SQLDatabaseDao {

    private final SQLDatabaseUserCosmetics userCosmetics;

    public SQLDatabaseUserCosmeticsDao(Model model) {
        super(model);

        this.userCosmetics = (SQLDatabaseUserCosmetics) model;
    }

    @Override
    public Object update(Model model) {
        userCosmetics.getCosmetics().forEach((cosmetic, status) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("enabled", status);

            getSqlDatabaseAdapter().updateWhere(userCosmetics.getTableNameWithPrefix(), data,
                    "uuid = '" + userCosmetics.getUuid().toString() + "' AND cosmetic = '" + cosmetic.getName() + "'");
        });

        return model;
    }

    @Override
    public Object save(Model model) {
        userCosmetics.getCosmetics().forEach((cosmetic, status) ->
                getSqlDatabaseAdapter().insert(userCosmetics.getTableNameWithPrefix(), null, userCosmetics.getUuid(), cosmetic.getName(), status));

        return model;
    }

    @Override
    public boolean delete(Model model) {
        userCosmetics.getCosmetics().forEach((cosmetic, status) -> getSqlDatabaseAdapter().deleteAllWhere(userCosmetics.getTableNameWithPrefix(),
                "uuid = '" + userCosmetics.getUuid() + "' AND cosmetic = '" + cosmetic.getName() + "'"));

        return true;
    }

}
