package pl.timsixth.minigameapi.api.module.mongodb.cosmetics;

import pl.timsixth.minigameapi.api.cosmetics.user.AbstractUserCosmetics;
import pl.timsixth.minigameapi.api.module.mongodb.core.MongoDbModel;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.UUID;

public class MongoDbUserCosmetics extends AbstractUserCosmetics implements MongoDbModel {

    public static final String COLLECTION_NAME = "users_cosmetics";

    public MongoDbUserCosmetics(UUID uuid) {
        super(uuid);
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public Dao getDao() {
        return new MongoDbUserCosmeticsDao(this);
    }
}
