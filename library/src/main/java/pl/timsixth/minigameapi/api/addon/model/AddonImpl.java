package pl.timsixth.minigameapi.api.addon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AddonImpl implements Addon {

    private final String name;
    private String repository;
    private String displayName;

    public AddonImpl(String name,String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public Plugin toPlugin() {
        return Bukkit.getPluginManager().getPlugin(name);
    }
    @Override
    public String getVersion() {
        return this.toPlugin().getDescription().getVersion();
    }
}
