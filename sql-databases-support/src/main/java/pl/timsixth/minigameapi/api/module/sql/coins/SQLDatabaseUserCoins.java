package pl.timsixth.minigameapi.api.module.sql.coins;

import pl.timsixth.minigameapi.api.coins.AbstractUserCoins;
import pl.timsixth.minigameapi.api.module.sql.core.DbModel;
import pl.timsixth.minigameapi.api.module.sql.core.annotations.Table;

import java.util.UUID;

@Table(name = "users_coins")
public class SQLDatabaseUserCoins extends AbstractUserCoins implements DbModel {

    public static final String TABLE_NAME = "users_coins";

    public SQLDatabaseUserCoins(UUID uuid, double coins) {
        super(uuid, coins);
    }
}
