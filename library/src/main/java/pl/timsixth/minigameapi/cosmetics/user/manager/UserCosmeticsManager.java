package pl.timsixth.minigameapi.cosmetics.user.manager;

import pl.timsixth.minigameapi.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.user.UserManager;

import java.util.List;
import java.util.UUID;

public interface UserCosmeticsManager<T extends UserCosmetics> extends UserManager<T> {

    List<T> getUsersCosmetics();

    void addUserCosmetics(T type);

    void removeUserCosmetics(T type);
}
