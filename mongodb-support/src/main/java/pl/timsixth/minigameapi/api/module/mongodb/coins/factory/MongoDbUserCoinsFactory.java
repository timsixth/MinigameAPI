package pl.timsixth.minigameapi.api.module.mongodb.coins.factory;

import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactory;
import pl.timsixth.minigameapi.api.module.mongodb.coins.MongoDbUserCoins;

import java.util.UUID;

public class MongoDbUserCoinsFactory implements UserCoinsFactory {

    @Override
    public UserCoins createUserCoins(UUID uuid, double coins) {
        return new MongoDbUserCoins(uuid, coins);
    }
}
