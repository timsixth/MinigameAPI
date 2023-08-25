package pl.timsixth.minigameapi.api.cosmetics.user.manager;

import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.user.UserManager;

/**
 * @param <T> every class which implemented {@link UserCosmetics}
 * @see UserManager
 */
public interface UserCosmeticsManager<T extends UserCosmetics> extends UserManager<T> {

}
