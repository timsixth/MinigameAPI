package pl.timsixth.minigameapi.plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.addon.manager.AddonManager;
import pl.timsixth.minigameapi.command.ParentCommand;
import pl.timsixth.minigameapi.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.plugin.subcommand.AddonSubCommand;
import pl.timsixth.minigameapi.util.ChatUtil;

import java.util.Arrays;
import java.util.List;

public class MiniGameAPICommand extends ParentCommand {


    public MiniGameAPICommand(CommandConfiguration defaultCommandConfiguration, AddonManager addonManager) {
        super("minigameapi.admin", true, false, true, defaultCommandConfiguration);
        getSubCommands().add(new AddonSubCommand(addonManager));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        List<String> messages = Arrays.asList(
                ChatUtil.chatColor("&7--------] &6&lMiniGameAPI &7[--------"),
                ChatUtil.chatColor("&7/&6minigameapi addon install <url> &7- Installs addon from Github link"),
                ChatUtil.chatColor("&7/&6minigameapi addon update <addon-name> &7- Updates addon"),
                ChatUtil.chatColor("&7/&6minigameapi addon enable <addon-name> &7- Enables addon"),
                ChatUtil.chatColor("&7/&6minigameapi addon disable <addon-name> &7- Disable addon"),
                ChatUtil.chatColor("&7/&6minigameapi addon list &7- List of all addons")
        );

        messages.forEach(player::sendMessage);

        return false;
    }
}
