package pl.timsixth.minigameapi.api.cosmetics.user;

import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Table;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.UUID;

/**
 * Adapter for UserCosmetics model which is saving in SQL database
 *
 * @see Table
 * @see AbstractUserCosmeticsAdapter
 * @see UserCosmetics
 * @see AbstractModelAdapter
 */
@Table(name = "users_cosmetics")
@Deprecated
public class SQLDatabaseUserCosmeticsAdapter extends AbstractUserCosmeticsAdapter {

    public static final String TABLE_NAME = "users_cosmetics";
    private final AbstractDbModel abstractDbModel;

    public SQLDatabaseUserCosmeticsAdapter(InitializableModel model, UUID uuid) {
        super(model, uuid);
        abstractDbModel = (AbstractDbModel) model;

        model.init(this);
    }

    @Override
    public Object update() {

        getCosmetics().forEach((cosmetic, status) -> {
//            QueryBuilder queryBuilder = new QueryBuilder();
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("enabled", status);
//
//
//            String query = queryBuilder.update(abstractDbModel.getTableNameWithPrefix(), data)
//                    .where("uuid = '" + getUuid().toString() + "' AND cosmetic = '" + cosmetic.getName() + "'")
//                    .build();
//
//            abstractDbModel.executeUpdate(query);
        });

        return this;
    }

    @Override
    public Object save() {

        getCosmetics().forEach((cosmetic, status) -> {
//            QueryBuilder queryBuilder = new QueryBuilder();
//
//            String query = queryBuilder.insert(abstractDbModel.getTableNameWithPrefix(), null, getUuid(), cosmetic.getName(), status).build();
//
//            abstractDbModel.executeUpdate(query);
        });
        return this;
    }

    @Override
    public boolean delete() {
        getCosmetics().forEach((cosmetic, status) -> {
//            QueryBuilder queryBuilder = new QueryBuilder();
//
//            String query = queryBuilder.deleteAll(abstractDbModel.getTableNameWithPrefix())
//                    .where("uuid = '" + getUuid() + "' AND cosmetic = '" + cosmetic.getName() + "'")
//                    .build();
//
//            abstractDbModel.executeUpdate(query);
        });

        return true;
    }

}
