package pl.timsixth.minigameapi.api.coins.manager;

import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.user.UserManager;

/**
 * @param <T> every class which implemented {@link UserCoins}
 * @see UserManager
 */
public interface UserCoinsManager<T extends UserCoins> extends UserManager<T> {

}
