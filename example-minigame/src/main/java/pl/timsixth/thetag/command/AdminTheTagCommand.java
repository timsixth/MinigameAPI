package pl.timsixth.thetag.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.ArenaImpl;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.util.ChatUtil;
import pl.timsixth.thetag.config.ConfigFile;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.NumberUtil;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
public class AdminTheTagCommand implements CommandExecutor {

    private final Settings settings;
    private final Messages messages;
    private final ArenaManager<ArenaFileModel> arenaManager;
    private final UserCoinsManager<UserCoinsDbModel> userCoinsManager;
    private final MenuManager menuManager;
    private final ConfigFile configFile;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("Only players can use this command");
            return true;
        }

        if (!sender.hasPermission(settings.getAdminPermission())) {
            sender.sendMessage(messages.getNoPermission());
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendMessagesWithAdminHelp(player);
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setlobby")) {
                settings.setLobbyLocation(player.getLocation());
                PlayerUtil.sendMessage(player, messages.getSetLobbySpawn());

            } else if (args[0].equalsIgnoreCase("reload")) {
                configFile.reloadFiles(Collections.singletonList(menuManager));
                PlayerUtil.sendMessage(player, messages.getReloadedFiles());
            } else {
                sendMessagesWithAdminHelp(player);
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (arenaManager.getArena(args[1]).isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getArenaExists());
                    return true;
                }
                arenaManager.addArena(new ArenaImpl(args[1], player.getLocation(), new HashMap<>()));
                PlayerUtil.sendMessage(player, messages.getArenaCreated());
            } else if (args[0].equalsIgnoreCase("setspawn")) {
                Optional<ArenaFileModel> arenaOptional = arenaManager.getArena(args[1]);
                if (!arenaOptional.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getArenaDoesNotExists());
                    return true;
                }
                ArenaFileModel arena = arenaOptional.get();
                arena.addLocation("spawn", player.getLocation());

                arena.update();

                PlayerUtil.sendMessage(player, messages.getSetGameSpawn());
            } else if (args[0].equalsIgnoreCase("setgamelobby")) {
                Optional<ArenaFileModel> arenaOptional = arenaManager.getArena(args[1]);
                if (!arenaOptional.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getArenaDoesNotExists());
                    return true;
                }
                ArenaFileModel arena = arenaOptional.get();

                arena.setLobbyLocation(player.getLocation());
                arena.update();

                PlayerUtil.sendMessage(player, messages.getSetLobbySpawn());
            } else {
                sendMessagesWithAdminHelp(player);
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("addcoins")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

                Optional<UserCoinsDbModel> userCoinsOptional = userCoinsManager.getUserByUuid(offlinePlayer.getUniqueId());
                if (!userCoinsOptional.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getPlayerDoesNotExits());
                    return true;
                }

                if (!NumberUtil.isDouble(args[2])) {
                    PlayerUtil.sendMessage(player, messages.getIsNotANumber());
                    return true;
                }

                double coins = Double.parseDouble(args[2]);

                if (coins <= 0) {
                    PlayerUtil.sendMessage(player, messages.getGreaterThanZero());
                    return true;
                }

                UserCoinsDbModel userCoinsDbModel = userCoinsOptional.get();

                userCoinsDbModel.addCoins(coins);
                PlayerUtil.sendMessage(player, messages.getAddedCoins().replace("{PLAYER_NAME}", player.getName()));
            } else if (args[0].equalsIgnoreCase("removecoins")) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

                Optional<UserCoinsDbModel> userCoinsOptional = userCoinsManager.getUserByUuid(offlinePlayer.getUniqueId());
                if (!userCoinsOptional.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getPlayerDoesNotExits());
                    return true;
                }

                if (!NumberUtil.isDouble(args[2])) {
                    PlayerUtil.sendMessage(player, messages.getIsNotANumber());
                    return true;
                }

                double coins = Double.parseDouble(args[2]);

                if (coins <= 0) {
                    PlayerUtil.sendMessage(player, messages.getGreaterThanZero());
                    return true;
                }

                UserCoinsDbModel userCoinsDbModel = userCoinsOptional.get();

                if (!userCoinsDbModel.hasCoins(coins)) {
                    PlayerUtil.sendMessage(player, messages.getDoesntHaveCoins().replace("{PLAYER_COINS}", String.valueOf(userCoinsDbModel.getCoins())));
                    return true;
                }

                userCoinsDbModel.removeCoins(coins);
                PlayerUtil.sendMessage(player, messages.getRemovedCoins().replace("{PLAYER_NAME}", player.getName()));
            } else {
                sendMessagesWithAdminHelp(player);
            }
        }

        return false;
    }

    private void sendMessagesWithAdminHelp(Player player) {
        for (String message : messages.getAdminHelp()) {
            PlayerUtil.sendMessage(player, ChatUtil.chatColor(message));
        }
    }

}
