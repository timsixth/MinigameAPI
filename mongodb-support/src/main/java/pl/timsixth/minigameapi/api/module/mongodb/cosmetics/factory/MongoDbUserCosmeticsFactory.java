package pl.timsixth.minigameapi.api.module.mongodb.cosmetics.factory;

import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactory;
import pl.timsixth.minigameapi.api.module.mongodb.cosmetics.MongoDbUserCosmetics;

import java.util.UUID;

public class MongoDbUserCosmeticsFactory implements UserCosmeticsFactory {
    @Override
    public UserCosmetics createUserCosmetics(UUID uuid) {
        return new MongoDbUserCosmetics(uuid);
    }
}
