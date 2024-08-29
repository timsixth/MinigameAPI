package pl.timsixth.minigameapi.api.game.user;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.minigameapi.api.util.options.CustomOptions;

/**
 * Represents the user which can rejoin to game
 */
public interface RecoverableUserGame extends UserGame, CustomOptions {

    /**
     * Gets items of player's inventory
     *
     * @return contents of player's inventory
     */
    ItemStack[] getContents();

    /**
     * Gets player's armor
     *
     * @return player's armor
     */
    ItemStack[] getArmor();

    /**
     * Gets latest location before leave from game
     *
     * @return latest location
     */
    Location getLatestLocation();

    /**
     * Gets latest exp before leave from game
     *
     * @return exp
     */
    float getExperience();

    /**
     * Gets latest level before leave from game
     *
     * @return level
     */
    int getLevel();

    /**
     * Gets latest food level before leave from game
     *
     * @return food level
     */
    int getFoodLevel();

    /**
     * Gets latest fire ticks before leave from game
     *
     * @return fire ticks
     */
    int getFireTicks();

    /**
     * Gets latest health before leave from game
     *
     * @return health
     */
    double getHealth();
}
