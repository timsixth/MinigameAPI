package pl.timsixth.minigameapi.api.module.sql.cosmetics;

import pl.timsixth.minigameapi.api.cosmetics.user.AbstractUserCosmetics;
import pl.timsixth.minigameapi.api.module.sql.core.DbModel;
import pl.timsixth.minigameapi.api.module.sql.core.annotations.Table;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

@Table(name = "users_cosmetics")
public class SQLDatabaseUserCosmetics extends AbstractUserCosmetics implements DbModel {

    public static final String TABLE_NAME = "users_cosmetics";

    public SQLDatabaseUserCosmetics(UUID uuid) {
        super(uuid);
    }

    @Override
    public Dao getDao() {
        return new SQLDatabaseUserCosmeticsDao(this);
    }
}
