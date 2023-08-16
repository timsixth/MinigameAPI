package pl.timsixth.minigameapi.addon.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.addon.model.Addon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class AddonManagerImpl implements AddonManager {

    private final MiniGameApiPlugin miniGameApiPlugin;

    private final List<Addon> addons = new ArrayList<>();

    @Override
    public void download(Addon addon) {

    }

    @Override
    public void update(Addon addon) {

    }

    @Override
    public void addAddon(Addon addon) {
        addons.add(addon);
    }

    @Override
    public void removeAddon(Addon addon) {
        addons.remove(addon);
    }

    @Override
    public Optional<Addon> getAddon(String name) {
        return addons.stream()
                .filter(addon -> addon.getName().equalsIgnoreCase(name))
                .findAny();
    }
}
