package pl.timsixth.minigameapi;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.minigameapi.bstats.Metrics;

@Getter
public class MiniGameApiPluginLite extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Server is using the &aT-MiniGameAPI &7by &atimsixth"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Library version: &a" + getDescription().getVersion()));

        new Metrics(this, 19564);
    }
}
