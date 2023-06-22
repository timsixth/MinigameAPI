package pl.timsixth.minigameapi.cosmetics.user;

import pl.timsixth.minigameapi.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.user.User;

import java.util.List;
import java.util.Map;

public interface UserCosmetics extends User {

    boolean hasCosmetic(Cosmetic cosmetic);

    void removeCosmetic(Cosmetic cosmetic);

    void addCosmetic(Cosmetic cosmetic);

    boolean isCosmeticEnable(Cosmetic cosmetic);

    void enableCosmetic(Cosmetic cosmetic);

    void disableCosmetic(Cosmetic cosmetic);

    Map<Cosmetic, Boolean> getCosmetics();

    void setCosmetics(Map<Cosmetic, Boolean> cosmetics);

    void resetAllCosmetics();

    void resetAllCosmeticsCategory(String category);

    List<Cosmetic> getAllCosmeticsCategory(String category);

    List<Cosmetic> getActiveCosmeticsByCategory(String category);
}
