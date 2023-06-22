package pl.timsixth.minigameapi;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.arena.ArenaImpl;
import pl.timsixth.minigameapi.util.ChatUtil;

public class MiniGameApiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Server is using the &aT-MiniGameAPI &7by &atimsixth"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Library version: &a" + getDescription().getVersion()));

        ConfigurationSerialization.registerClass(ArenaImpl.class);
    }

}
