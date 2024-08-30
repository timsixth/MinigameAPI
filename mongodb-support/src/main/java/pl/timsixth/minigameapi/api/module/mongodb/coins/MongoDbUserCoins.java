package pl.timsixth.minigameapi.api.module.mongodb.coins;

import pl.timsixth.minigameapi.api.coins.AbstractUserCoins;
import pl.timsixth.minigameapi.api.module.mongodb.core.MongoDbModel;
import pl.timsixth.minigameapi.api.module.mongodb.core.dao.MongoDbDao;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

public class MongoDbUserCoins extends AbstractUserCoins implements MongoDbModel {

    public MongoDbUserCoins(UUID uuid, double coins) {
        super(uuid, coins);
    }

    @Override
    public String getCollectionName() {
        return "users_coins";
    }

    @Override
    public Dao getDao() {
        return new MongoDbDao(this);
    }
}
