package pl.timsixth.minigameapi.addon;

import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.addon.model.Addon;
import pl.timsixth.minigameapi.addon.model.AddonImpl;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class MiniGameAddon extends JavaPlugin {

    @Override
    public void onEnable() {
        MiniGameApiPlugin miniGameApiPlugin = MiniGameApiPlugin.getPlugin(MiniGameApiPlugin.class);

        Addon addon;
        try {
            addon = new AddonImpl(this.getName(), this.getDescription().getVersion(), this.getDescription().getAuthors(),
                    new URL(getGithubLink()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        miniGameApiPlugin.getAddonManager().addAddon(addon);
    }

    public String getGithubLink() {
        return "";
    }
}
