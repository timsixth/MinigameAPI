package pl.timsixth.minigameapi.api.module.mongodb.coins;

import pl.timsixth.minigameapi.api.coins.AbstractUserCoins;
import pl.timsixth.minigameapi.api.module.mongodb.core.MongoDbModel;
import pl.timsixth.minigameapi.api.module.mongodb.core.dao.MongoDbDao;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

public class MongoDbUserCoins extends AbstractUserCoins implements MongoDbModel {

    public static final String COLLECTION_NAME = "users_coins";

    public MongoDbUserCoins(UUID uuid, double coins) {
        super(uuid, coins);
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Dao getDao() {
        return new MongoDbDao(this);
    }
}
