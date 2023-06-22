package pl.timsixth.minigameapi.cosmetics.user.manager;

import pl.timsixth.minigameapi.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.user.UserManager;

import java.util.List;

/**
 * @param <T> every class which implemented {@link UserCosmetics}
 * @see UserManager
 */
public interface UserCosmeticsManager<T extends UserCosmetics> extends UserManager<T> {
    /**
     * @return list of users
     */
    List<T> getUsersCosmetics();

    /**
     * Adds new user
     *
     * @param type every class which implemented {@link UserCosmetics}
     */
    void addUserCosmetics(T type);

    /**
     * Removes new user
     *
     * @param type every class which implemented {@link UserCosmetics}
     */
    void removeUserCosmetics(T type);
}
