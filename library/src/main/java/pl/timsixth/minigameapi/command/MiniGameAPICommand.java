package pl.timsixth.minigameapi.command;

import org.bukkit.command.CommandSender;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.tabcompleter.BaseTabCompleter;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.command.subcommand.AddonSubCommand;
import pl.timsixth.minigameapi.config.Messages;
import pl.timsixth.minigameapi.tabcompleter.MiniGameAPITabCompleter;

public class MiniGameAPICommand extends ParentCommand {

    private final Messages messages;
    private final AddonManager addonManager;

    public MiniGameAPICommand(CommandConfiguration defaultCommandConfiguration, AddonManager addonManager, Messages messages, MiniGameApiPlugin miniGameApiPlugin) {
        super("minigameapi.admin", true, false, true, defaultCommandConfiguration);
        this.messages = messages;
        this.addonManager = addonManager;
        getSubCommands().add(new AddonSubCommand(addonManager, messages, miniGameApiPlugin));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {

        messages.getAddonsHelp().forEach(sender::sendMessage);

        return false;
    }

    @Override
    public String getName() {
        return "minigameapi";
    }

    @Override
    public BaseTabCompleter getTabCompleter() {
        return new MiniGameAPITabCompleter(this, addonManager);
    }
}
