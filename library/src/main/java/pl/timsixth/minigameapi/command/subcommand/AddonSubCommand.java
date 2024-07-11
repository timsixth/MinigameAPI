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
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Addons system will be removed
 * @deprecated
 */
@Deprecated
@RequiredArgsConstructor
public class AddonSubCommand implements SubCommand {

    private final AddonManager addonManager;
    private final Messages messages;
    private final MiniGameApiPlugin miniGameApiPlugin;

    private final Pattern REPOSITORY_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,}/[A-Za-z0-9]{3,}");

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
                Addon addon = getAddon(args, sender);
                if (addon == null) return true;

                Plugin plugin = addon.toPlugin();

                if (plugin.isEnabled()) {
                    sender.sendMessage(messages.getAddonAlreadyEnabled());
                    return true;
                }

                Bukkit.getPluginManager().enablePlugin(plugin);

                sender.sendMessage(messages.getAddonEnabled());
            } else if (args[1].equalsIgnoreCase("disable")) {
                Addon addon = getAddon(args, sender);
                if (addon == null) return true;

                Plugin plugin = addon.toPlugin();

                if (!plugin.isEnabled()) {
                    sender.sendMessage(messages.getAddonAlreadyDisabled());
                    return true;
                }

                Bukkit.getPluginManager().disablePlugin(plugin);
                sender.sendMessage(messages.getAddonDisabled());
            } else if (args[1].equalsIgnoreCase("update")) {
                Addon addon = getAddon(args, sender);
                if (addon == null) return true;

                if (Bukkit.getPluginManager().getPlugin("PlugManX") == null) {
                    sender.sendMessage(ChatUtil.chatColor("&cUpdate command requires PlugManX plugin to work."));
                    return true;
                }

                Bukkit.getScheduler().runTaskAsynchronously(miniGameApiPlugin, () -> {
                    try {
                        addonManager.update(addon);
                    } catch (IOException | InvalidPluginException e) {
                        Bukkit.getLogger().severe("Error: " + e);
                    }
                });

                sender.sendMessage(messages.getAddonUpdated());
            }
        } else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("install")) {
                String repository = args[2];

                Matcher matcher = REPOSITORY_PATTERN.matcher(repository);
                if (!matcher.matches()) {
                    sender.sendMessage(messages.getInvalidRepositoryName());
                    return true;
                }
                sender.sendMessage(messages.getAddonDownloading());
                Bukkit.getScheduler().runTaskAsynchronously(miniGameApiPlugin, () -> {
                    try {
                        File file = addonManager.download(repository, args[3] /*version*/);

                        addonManager.loadAddon(file);
                    } catch (IOException | InvalidPluginException e) {
                        Bukkit.getLogger().severe("Error: " + e);
                    }
                });
                sender.sendMessage(messages.getAddonHasDownloaded());
            }
        }
        return false;
    }

    private Addon getAddon(String[] args, CommandSender sender) {
        String addonName = args[2];

        Optional<Addon> addonOptional = addonManager.getAddon(addonName);
        if (!addonOptional.isPresent()) {
            sender.sendMessage(messages.getAddonDoesNotExists());
            return null;
        }

        return addonOptional.get();
    }

    @Override
    public String getName() {
        return "addon";
    }
}
