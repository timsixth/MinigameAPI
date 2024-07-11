package pl.timsixth.minigameapi;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidPluginException;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.addon.manager.AddonManagerImpl;
import pl.timsixth.minigameapi.api.command.CommandRegistration;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultCommandConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.util.FileUtil;
import pl.timsixth.minigameapi.command.MiniGameAPICommand;
import pl.timsixth.minigameapi.config.Messages;

import java.io.File;
/**
 *
 * @deprecated This class will be replaced by {@link MiniGameApiPluginLite}
 */
@Deprecated
@Getter
public final class MiniGameApiPlugin extends MiniGameApiPluginLite {

    private AddonManager addonManager;

    private File addonsFile;

    @Override
    @SneakyThrows
    public void onEnable() {
        super.onEnable();

        this.getConfig().options().copyDefaults(true);
        saveConfig();

        Messages messages = new Messages(this);

        addonManager = new AddonManagerImpl(this, addonsFile);
        CommandConfiguration commandConfiguration = new DefaultCommandConfigurator().configure();

        new CommandRegistration(this).registerCommandWithTabCompleter(new MiniGameAPICommand(commandConfiguration, addonManager, messages, this));

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            addonsFile = FileUtil.createDirectory(getDataFolder(), "addons");

            try {
                addonManager.loadAddons(addonsFile);
            } catch (InvalidPluginException e) {
                Bukkit.getLogger().severe(e.getMessage());
            }
        });
    }
}
