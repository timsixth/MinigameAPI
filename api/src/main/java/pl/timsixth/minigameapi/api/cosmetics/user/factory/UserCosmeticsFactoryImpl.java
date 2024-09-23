package pl.timsixth.minigameapi.api.cosmetics.user.factory;

import pl.timsixth.minigameapi.api.cosmetics.user.SingleYamlUserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;

import java.util.UUID;

public class UserCosmeticsFactoryImpl implements UserCosmeticsFactory {
    @Override
    public UserCosmetics createUserCosmetics(UUID uuid) {
        return new SingleYamlUserCosmetics(uuid);
    }
}
