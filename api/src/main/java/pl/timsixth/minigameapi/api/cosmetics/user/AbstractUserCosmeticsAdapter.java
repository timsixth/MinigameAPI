package pl.timsixth.minigameapi.api.cosmetics.user;

import lombok.Getter;
import lombok.Setter;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.*;
/**
 * Represents every {@link UserCosmetics} model
 *
 * @see AbstractModelAdapter
 * @see InitializableModel
 *
 * @deprecated there is new models system
 */
@Getter
@Setter
@Deprecated
public abstract class AbstractUserCosmeticsAdapter extends AbstractModelAdapter implements UserCosmetics {

    @Id
    @IdSection
    private final UUID uuid;
    private Map<Cosmetic, Boolean> cosmetics;

    public AbstractUserCosmeticsAdapter(InitializableModel model, UUID uuid) {
        super(model);
        this.uuid = uuid;
        this.cosmetics = new HashMap<>();
    }

    @Override
    public boolean hasCosmetic(Cosmetic cosmetic) {
        return cosmetics.containsKey(cosmetic);
    }

    @Override
    public void removeCosmetic(Cosmetic cosmetic) {
        cosmetics.remove(cosmetic);
        delete();
    }

    @Override
    public void addCosmetic(Cosmetic cosmetic) {
        cosmetics.put(cosmetic, false);
        delete();
        save();
    }

    @Override
    public boolean isCosmeticEnable(Cosmetic cosmetic) {
        return cosmetics.getOrDefault(cosmetic, false);
    }

    @Override
    public void enableCosmetic(Cosmetic cosmetic) {
        if (isCosmeticEnable(cosmetic)) return;

        cosmetics.replace(cosmetic, true);
        update();
    }

    @Override
    public void disableCosmetic(Cosmetic cosmetic) {
        if (!isCosmeticEnable(cosmetic)) return;

        cosmetics.replace(cosmetic, false);
        update();
    }

    @Override
    public void resetAllCosmetics() {
        cosmetics.forEach((cosmetic, status) -> cosmetics.replace(cosmetic, false));

        update();
    }

    @Override
    public void resetAllCosmeticsCategory(String category) {
        List<Cosmetic> allCosmeticsCategory = getAllCosmeticsCategory(category);

        allCosmeticsCategory.forEach(cosmetic -> cosmetics.replace(cosmetic, false));

        update();
    }

    @Override
    public List<Cosmetic> getAllCosmeticsCategory(String category) {
        List<Cosmetic> cosmetics = new ArrayList<>();

        this.cosmetics.forEach((cosmetic, status) -> {
            if (cosmetic.getName().toUpperCase().contains(category.toUpperCase())) {
                cosmetics.add(cosmetic);
            }
        });

        return cosmetics;
    }

    @Override
    public List<Cosmetic> getActiveCosmeticsByCategory(String category) {
        List<Cosmetic> cosmetics = new ArrayList<>();

        this.cosmetics.forEach((cosmetic, status) -> {
            if (cosmetic.getName().toUpperCase().contains(category.toUpperCase())) {
                if (this.cosmetics.get(cosmetic)) cosmetics.add(cosmetic);
            }
        });

        return cosmetics;
    }
}
