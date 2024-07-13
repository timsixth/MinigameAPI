package pl.timsixth.minigameapi.api.cosmetics.user;

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
 * @deprecated use SQL module
 */
@Table(name = "users_cosmetics")
@Deprecated
public class SQLDatabaseUserCosmeticsAdapter extends AbstractUserCosmeticsAdapter {

    public static final String TABLE_NAME = "users_cosmetics";

    public SQLDatabaseUserCosmeticsAdapter(InitializableModel model, UUID uuid) {
        super(model, uuid);

        model.init(this);
    }

    @Override
    public Object update() {
        throw new UnsupportedOperationException("use SQL module");
    }

    @Override
    public Object save() {
        throw new UnsupportedOperationException("use SQL module");
    }

    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("use SQL module");
    }

}
