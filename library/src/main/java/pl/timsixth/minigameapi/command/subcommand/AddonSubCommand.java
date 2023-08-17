package pl.timsixth.minigameapi.command.subcommand;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.api.addon.manager.AddonManager;
import pl.timsixth.minigameapi.api.addon.model.Addon;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.config.Messages;
import pl.timsixth.minigameapi.util.ChatUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class AddonSubCommand implements SubCommand {

    private final AddonManager addonManager;
    private final Messages messages;
    private final MiniGameApiPlugin miniGameApiPlugin;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                sender.sendMessage(ChatUtil.chatColor(messages.getInstalledAddons() + " &e(" + addonManager.getAddons().size() + "):"));
                addonManager.getAddons().forEach(addon -> {
                    String addonStatus = addon.toPlugin().isEnabled() ? "&a" + addon.getDisplayName() : "&c" + addon.getDisplayName();
                    sender.sendMessage(ChatUtil.chatColor("&e- " + addonStatus + " &6v" + addon.getVersion()));
                });
                return true;
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("enable")) {
                Plugin plugin = getPlugin(args, sender);
                if (plugin == null) return true;

                if (plugin.isEnabled()) {
                    sender.sendMessage(messages.getAddonAlreadyEnabled());
                    return true;
                }

                Bukkit.getPluginManager().enablePlugin(plugin);

                sender.sendMessage(messages.getAddonEnabled());
            } else if (args[1].equalsIgnoreCase("disable")) {
                Plugin plugin = getPlugin(args, sender);
                if (plugin == null) return true;

                if (!plugin.isEnabled()) {
                    sender.sendMessage(messages.getAddonAlreadyDisabled());
                    return true;
                }

                Bukkit.getPluginManager().disablePlugin(plugin);
                sender.sendMessage(messages.getAddonDisabled());
            }
        } else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("install")) {
                String version;

                if (args[3] != null) version = args[3];
                else {
                    version = "latest";
                }
                sender.sendMessage(messages.getAddonDownloading());
                Bukkit.getScheduler().runTaskAsynchronously(miniGameApiPlugin, () -> {
                    try {
                        File file = addonManager.download(args[2]  /*GitHub repository*/, version);

                        addonManager.loadAddon(file);
                    } catch (IOException | InvalidPluginException e) {
                        throw new RuntimeException(e);
                    }
                });
                sender.sendMessage(messages.getAddonHasDownloaded());
            }
        }
        return false;
    }

    private Plugin getPlugin(String[] args, CommandSender sender) {
        String addonName = args[2];

        Optional<Addon> addonOptional = addonManager.getAddon(addonName);
        if (!addonOptional.isPresent()) {
            sender.sendMessage(messages.getAddonDoesNotExists());
            return null;
        }

        return addonOptional.get().toPlugin();
    }

    @Override
    public String getName() {
        return "addon";
    }
}
