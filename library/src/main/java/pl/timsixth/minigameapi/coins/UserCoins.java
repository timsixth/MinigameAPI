package pl.timsixth.minigameapi.coins;

import pl.timsixth.minigameapi.user.User;

/**
 * @see User
 */
public interface UserCoins extends User {
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
