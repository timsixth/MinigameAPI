package pl.timsixth.minigameapi;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.addon.manager.AddonManagerImpl;
import pl.timsixth.minigameapi.api.arena.ArenaImpl;
import pl.timsixth.minigameapi.api.command.CommandRegistration;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultCommandConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.bstats.Metrics;
import pl.timsixth.minigameapi.command.MiniGameAPICommand;
import pl.timsixth.minigameapi.config.Messages;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.minigameapi.api.util.FileUtil;

import java.io.File;

@Getter
public final class MiniGameApiPlugin extends JavaPlugin {

    private AddonManager addonManager;

    private File addonsFile;

    @Override
    @SneakyThrows
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Server is using the &aT-MiniGameAPI &7by &atimsixth"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.chatColor("&7Library version: &a" + getDescription().getVersion()));

        ConfigurationSerialization.registerClass(ArenaImpl.class);

        this.getConfig().options().copyDefaults(true);
        saveConfig();

        new Metrics(this, 19564);

        Messages messages = new Messages(this);

        addonManager = new AddonManagerImpl(this);
        CommandConfiguration commandConfiguration = new DefaultCommandConfigurator().configure();

        new CommandRegistration(this).registerCommandWithTabCompleter(new MiniGameAPICommand(commandConfiguration, addonManager, messages, this));

        addonsFile = FileUtil.createDirectory(getDataFolder(), "addons");

        addonManager.loadAddons(addonsFile);
    }
}
