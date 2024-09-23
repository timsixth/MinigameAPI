package pl.timsixth.minigameapi.api.module.sql.cosmetics.factory;

import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactory;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.SQLDatabaseUserCosmetics;

import java.util.UUID;

public class SQLDatabaseUserCosmeticsFactory implements UserCosmeticsFactory {
    @Override
    public UserCosmetics createUserCosmetics(UUID uuid) {
        return new SQLDatabaseUserCosmetics(uuid);
    }
}
