package pl.timsixth.minigameapi.api.addon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Implementation of {@link Addon}
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AddonImpl implements Addon {

    private final String name;
    private final String displayName;
    private final File pluginFile;
    private String repository;

    @Override
    public Plugin toPlugin() {
        return Bukkit.getPluginManager().getPlugin(name);
    }

    @Override
    public String getVersion() {
        return this.toPlugin().getDescription().getVersion();
    }
}
