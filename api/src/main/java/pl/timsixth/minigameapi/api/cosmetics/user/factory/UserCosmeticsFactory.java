package pl.timsixth.minigameapi.api.cosmetics.user.factory;

import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;

import java.util.UUID;

public interface UserCosmeticsFactory {

    UserCosmetics createUserCosmetics(UUID uuid);
}
