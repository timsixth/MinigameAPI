package pl.timsixth.minigameapi.command;

import org.bukkit.command.CommandSender;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.config.Messages;
import pl.timsixth.minigameapi.command.subcommand.AddonSubCommand;

public class MiniGameAPICommand extends ParentCommand {

    private final Messages messages;

    public MiniGameAPICommand(CommandConfiguration defaultCommandConfiguration, AddonManager addonManager, Messages messages, MiniGameApiPlugin miniGameApiPlugin) {
        super("minigameapi.admin", true, false, true, defaultCommandConfiguration);
        this.messages = messages;
        getSubCommands().add(new AddonSubCommand(addonManager, messages, miniGameApiPlugin));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {

        messages.getAddonsHelp().forEach(sender::sendMessage);

        return false;
    }
}
