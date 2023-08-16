package pl.timsixth.minigameapi.plugin.subcommand;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.timsixth.minigameapi.addon.manager.AddonManager;
import pl.timsixth.minigameapi.addon.model.Addon;
import pl.timsixth.minigameapi.command.SubCommand;
import pl.timsixth.minigameapi.util.ChatUtil;

import java.util.Optional;

@RequiredArgsConstructor
public class AddonSubCommand implements SubCommand {

    private final AddonManager addonManager;

    /*
    ChatUtil.chatColor("&7--------] &6&lMiniGameAPI &7[--------"),
                ChatUtil.chatColor("&7/&6minigameapi addon install <url> &7- Installs addon from Github link"),
                ChatUtil.chatColor("&7/&6minigameapi addon update <addon-name> &7- Updates addon"),
                ChatUtil.chatColor("&7/&6minigameapi addon enable <addon-name> &7- Enables addon"),
                ChatUtil.chatColor("&7/&6minigameapi addon disable <addon-name> &7- Disable addon"),
                ChatUtil.chatColor("&7/&6minigameapi addon list &7- List of all addons")
     */
    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                player.sendMessage(ChatUtil.chatColor("&aInstalled addons &e(" + addonManager.getAddons().size() + "):"));
                addonManager.getAddons().forEach(addon -> {
                    String addonStatus = addon.toPlugin().isEnabled() ? "&a" + addon.getName() : "&c" + addon.getName();
                    player.sendMessage(ChatUtil.chatColor("&e-" + addonStatus + "&6v" + addon.getVersion()));
                });
                return true;
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("enable")) {
                Plugin plugin = getPlugin(args, player);
                if (plugin == null) return true;

                if (plugin.isEnabled()) {
                    player.sendMessage(ChatUtil.chatColor("&cAddon is already enabled"));
                    return true;
                }

                Bukkit.getPluginManager().enablePlugin(plugin);

                player.sendMessage(ChatUtil.chatColor("&aYou have enabled addon"));
            } else if (args[2].equalsIgnoreCase("disable")) {
                Plugin plugin = getPlugin(args, player);
                if (plugin == null) return true;

                if (!plugin.isEnabled()) {
                    player.sendMessage(ChatUtil.chatColor("&cAddon is already disabled"));
                    return true;
                }

                Bukkit.getPluginManager().disablePlugin(plugin);
                player.sendMessage(ChatUtil.chatColor("&aYou have disabled addon"));
            }
        }
        return false;
    }

    private Plugin getPlugin(String[] args, Player player) {
        String addonName = args[2];

        Optional<Addon> addonOptional = addonManager.getAddon(addonName);
        if (!addonOptional.isPresent()) {
            player.sendMessage(ChatUtil.chatColor("&cAddon does not exists"));
            return null;
        }

        return addonOptional.get().toPlugin();
    }

    @Override
    public String getName() {
        return "addon";
    }
}
