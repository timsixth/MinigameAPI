package pl.timsixth.minigameapi.api.addon.model;

import org.bukkit.plugin.Plugin;

public interface Addon {

    String getName();
    String getVersion();
    String getRepository();
    String getDisplayName();
    Plugin toPlugin();
}
