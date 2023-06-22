package pl.timsixth.minigameapi.cosmetics;

import java.util.List;
import java.util.Optional;

public interface CosmeticsManager {

    Optional<Cosmetic> getCosmeticByName(String name);

    List<Cosmetic> getCosmetics();

    void addCosmetic(Cosmetic cosmetic);

    void removeCosmetic(Cosmetic cosmetic);
}
