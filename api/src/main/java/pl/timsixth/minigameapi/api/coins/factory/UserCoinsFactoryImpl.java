package pl.timsixth.minigameapi.api.coins.factory;

import pl.timsixth.minigameapi.api.coins.SingleYamlUserCoins;
import pl.timsixth.minigameapi.api.coins.UserCoins;

import java.util.UUID;

/**
 * Implementation of {@link UserCoinsFactory}
 */
public class UserCoinsFactoryImpl implements UserCoinsFactory {
    @Override
    public UserCoins createUserCoins(UUID uuid) {
        return createUserCoins(uuid, 0.0);
    }

    @Override
    public UserCoins createUserCoins(UUID uuid, double coins) {
        return new SingleYamlUserCoins(uuid, coins);
    }

}
