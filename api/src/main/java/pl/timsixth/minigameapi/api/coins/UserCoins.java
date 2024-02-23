package pl.timsixth.minigameapi.api.coins;

import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.user.User;

/**
 * @see User
 */
public interface UserCoins extends User, Model {
    /**
     * @return user's coins
     */
    double getCoins();

    /**
     * Sets coins
     *
     * @param coins new amount of coins
     */
    void setCoins(double coins);

    /**
     * Adds coins
     *
     * @param coins coins to add
     */
    void addCoins(double coins);

    /**
     * Removes coins
     *
     * @param coins coins to remove
     */
    void removeCoins(double coins);

    /**
     * @param coins to check
     * @return true if player has coins otherwise false
     */
    boolean hasCoins(double coins);
}
