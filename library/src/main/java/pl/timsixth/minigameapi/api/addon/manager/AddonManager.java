package pl.timsixth.minigameapi.api.addon.manager;

import org.bukkit.plugin.InvalidPluginException;
import pl.timsixth.minigameapi.api.addon.model.Addon;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AddonManager {

    File download(String addonName, String version) throws IOException;

    void update(Addon addon);

    List<Addon> getAddons();

    Optional<Addon> getAddon(String name);

    void loadAddons(File addons) throws InvalidPluginException;

    void loadAddon(File file) throws InvalidPluginException;
}
