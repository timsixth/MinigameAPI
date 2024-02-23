package pl.timsixth.minigameapi.api.cosmetics.impl;

import lombok.Getter;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of {@link CosmeticsManager}
 *
 * Every cosmetics must be registered in main class
 */
@Getter
public class CosmeticsManagerImpl implements CosmeticsManager {

    private final List<Cosmetic> cosmetics = new ArrayList<>();

    @Override
    public Optional<Cosmetic> getCosmeticByName(String name) {
        return cosmetics.stream()
                .filter(cosmetic -> cosmetic.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public void addCosmetic(Cosmetic cosmetic) {
        cosmetics.add(cosmetic);
    }

    @Override
    public void removeCosmetic(Cosmetic cosmetic) {
        cosmetics.remove(cosmetic);
    }

    @Override
    public void addCosmetics(Cosmetic... cosmetics) {
        if (cosmetics.length == 0) throw new IllegalArgumentException("Cosmetics can not be empty");

        this.cosmetics.addAll(Arrays.asList(cosmetics));
    }
}
