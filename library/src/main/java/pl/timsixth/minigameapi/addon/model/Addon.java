package pl.timsixth.minigameapi.addon.model;

import org.bukkit.plugin.Plugin;

import java.util.List;
import java.net.URL;

public interface Addon {

    String getName();
    List<String> getAuthors();
    String getVersion();
    URL getGithubUrl();
    Plugin toPlugin();
}
