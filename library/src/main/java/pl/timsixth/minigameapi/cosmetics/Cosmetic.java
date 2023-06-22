package pl.timsixth.minigameapi.cosmetics;

import org.bukkit.entity.Player;

/**
 * Represents every cosmetic
 */
public interface Cosmetic {
    /**
     * @return cosmetics name (recommended name: (CATEGORY_NAME_COSMETIC_NAME) e.g. HIT_HEART)
     */
    String getName();

    /**
     * Shows cosmetic to player
     *
     * @param player to show cosmetic
     */
    void show(Player player);
}
