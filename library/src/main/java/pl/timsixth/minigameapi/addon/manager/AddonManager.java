package pl.timsixth.minigameapi.addon.manager;

import pl.timsixth.minigameapi.addon.model.Addon;

import java.util.List;
import java.util.Optional;

public interface AddonManager {

    void download(Addon addon);

    void update(Addon addon);

    List<Addon> getAddons();

    void addAddon(Addon addon);

    void removeAddon(Addon addon);

    Optional<Addon> getAddon(String name);
}
