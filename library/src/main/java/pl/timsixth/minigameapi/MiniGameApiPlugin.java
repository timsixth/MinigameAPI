package pl.timsixth.minigameapi;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.addon.manager.AddonManager;
import pl.timsixth.minigameapi.addon.manager.AddonManagerImpl;
import pl.timsixth.minigameapi.arena.ArenaImpl;
import pl.timsixth.minigameapi.configuration.configurators.DefaultCommandConfigurator;
import pl.timsixth.minigameapi.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.plugin.MiniGameAPICommand;
import pl.timsixth.minigameapi.util.ChatUtil;

@Getter
public class MiniGameApiPlugin extends JavaPlugin {
    private AddonManager addonManager;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Server is using the &aT-MiniGameAPI &7by &atimsixth"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Library version: &a" + getDescription().getVersion()));

        ConfigurationSerialization.registerClass(ArenaImpl.class);

        addonManager = new AddonManagerImpl(this);
        CommandConfiguration commandConfiguration = new DefaultCommandConfigurator().configure();

        getCommand("minigameapi").setExecutor(new MiniGameAPICommand(commandConfiguration,addonManager));

    }

}
