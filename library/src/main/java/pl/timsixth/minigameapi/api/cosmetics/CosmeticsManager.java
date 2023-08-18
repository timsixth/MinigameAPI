package pl.timsixth.minigameapi.api.cosmetics;

import java.util.List;
import java.util.Optional;

/**
 * The class which manage cosmetics system
 */
public interface CosmeticsManager {
    /**
     * Gets cosmetics by name
     *
     * @param name cosmetic name
     * @return Optional of cosmetic
     */
    Optional<Cosmetic> getCosmeticByName(String name);

    /**
     * @return list of cosmetics
     */
    List<Cosmetic> getCosmetics();

    /**
     * Adds new cosmetic (Every cosmetic must be registered in main class)
     *
     * @param cosmetic cosmetic to add
     */
    void addCosmetic(Cosmetic cosmetic);

    /**
     * Removes new cosmetic
     *
     * @param cosmetic cosmetic to remove
     */
    void removeCosmetic(Cosmetic cosmetic);
}
