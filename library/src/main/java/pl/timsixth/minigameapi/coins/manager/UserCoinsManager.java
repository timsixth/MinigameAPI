package pl.timsixth.minigameapi.coins.manager;

import pl.timsixth.minigameapi.coins.UserCoins;
import pl.timsixth.minigameapi.user.UserManager;

import java.util.List;

/**
 * @param <T> every class which implemented {@link UserCoins}
 * @see UserManager
 */
public interface UserCoinsManager<T extends UserCoins> extends UserManager<T> {
    /**
     * @return list of users
     */
    List<T> getUsers();

    /**
     * Adds new user
     *
     * @param type every class which implemented {@link UserCoins}
     */
    void addUser(T type);

    /**
     * Removes user
     *
     * @param type every class which implemented {@link UserCoins}
     */
    void removeUser(T type);
}
