package pl.timsixth.minigameapi.cosmetics.user;

import pl.timsixth.minigameapi.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.user.User;

import java.util.List;
import java.util.Map;

public interface UserCosmetics extends User {
    /**
     * Checks having cosmetic
     *
     * @param cosmetic cosmetic to check
     * @return true if player has cosmetic otherwise false
     */
    boolean hasCosmetic(Cosmetic cosmetic);

    /**
     * Removes cosmetic
     *
     * @param cosmetic cosmetic to remove
     */
    void removeCosmetic(Cosmetic cosmetic);

    /**
     * Adds cosmetic
     *
     * @param cosmetic cosmetic to add
     */
    void addCosmetic(Cosmetic cosmetic);

    /**
     * Checks that cosmetic is enabled
     *
     * @param cosmetic cosmetic to check
     * @return true if cosmetic is enabled otherwise false
     */
    boolean isCosmeticEnable(Cosmetic cosmetic);

    /**
     * Enables cosmetic
     *
     * @param cosmetic cosmetic to enable
     */
    void enableCosmetic(Cosmetic cosmetic);

    /**
     * Disables cosmetic
     *
     * @param cosmetic cosmetic to disable
     */
    void disableCosmetic(Cosmetic cosmetic);

    /**
     * Gets all user cosmetics
     *
     * @return map with all cosmetics
     */
    Map<Cosmetic, Boolean> getCosmetics();

    /**
     * Sets cosmetics
     *
     * @param cosmetics map to set
     */
    void setCosmetics(Map<Cosmetic, Boolean> cosmetics);

    /**
     * Rest all cosmetics for user
     */
    void resetAllCosmetics();

    /**
     * Rest all cosmetics for one category
     *
     * @param category category to reset
     */
    void resetAllCosmeticsCategory(String category);

    /**
     * Gets all cosmetics by category name
     *
     * @param category category name
     * @return all cosmetics with this category
     */
    List<Cosmetic> getAllCosmeticsCategory(String category);

    /**
     * Gets all enabled cosmetics with this category
     *
     * @param category category name
     * @return all enabled cosmetics with this category
     */
    List<Cosmetic> getActiveCosmeticsByCategory(String category);
}
