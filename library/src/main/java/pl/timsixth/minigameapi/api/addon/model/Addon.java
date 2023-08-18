package pl.timsixth.minigameapi.api.addon.model;

import org.bukkit.plugin.Plugin;

import java.io.File;

public interface Addon {

    String getName();
    String getVersion();
    String getRepository();
    String getDisplayName();
    File getPluginFile();
    Plugin toPlugin();
}
