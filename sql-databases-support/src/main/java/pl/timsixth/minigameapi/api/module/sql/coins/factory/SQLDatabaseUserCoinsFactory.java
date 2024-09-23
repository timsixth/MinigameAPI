package pl.timsixth.minigameapi.api.module.sql.coins.factory;

import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactory;
import pl.timsixth.minigameapi.api.module.sql.coins.SQLDatabaseUserCoins;

import java.util.UUID;

/**
 * Implementation of {@link UserCoinsFactory}
 */
public class SQLDatabaseUserCoinsFactory implements UserCoinsFactory {
    @Override
    public UserCoins createUserCoins(UUID uuid) {
        return createUserCoins(uuid, 0.0);
    }

    @Override
    public UserCoins createUserCoins(UUID uuid, double coins) {
        return new SQLDatabaseUserCoins(uuid, coins);
    }

}
