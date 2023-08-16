package pl.timsixth.minigameapi.addon.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.net.URL;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AddonImpl implements Addon {

    private final String name;
    private final String version;
    private List<String> authors;
    private URL githubUrl;

    public AddonImpl(String name, String version, List<String> authors, URL githubUrl) {
        this.name = name;
        this.version = version;
        this.authors = authors;
        this.githubUrl = githubUrl;
    }

    public AddonImpl(String name, String version, URL githubUrl) {
        this.name = name;
        this.version = version;
        this.githubUrl = githubUrl;
    }

    public AddonImpl(String name, String version, List<String> authors) {
        this.name = name;
        this.version = version;
        this.authors = authors;
    }

    @Override
    public Plugin toPlugin() {
        return Bukkit.getPluginManager().getPlugin(name);
    }
}
