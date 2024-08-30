package pl.timsixth.minigameapi.api.coins.factory;

import pl.timsixth.minigameapi.api.coins.UserCoins;

import java.util.UUID;

/**
 * Creates instance of {@link UserCoins}
 */
public interface UserCoinsFactory {

    /**
     * Creates user, by default coins will be set to 0
     *
     * @param uuid player's uuid
     * @return created user
     */
    default UserCoins createUserCoins(UUID uuid) {
        return createUserCoins(uuid, 0);
    }

    /**
     * Creates user
     *
     * @param uuid  player's uuid
     * @param coins default coins amount
     * @return created user
     */
    UserCoins createUserCoins(UUID uuid, double coins);
}
