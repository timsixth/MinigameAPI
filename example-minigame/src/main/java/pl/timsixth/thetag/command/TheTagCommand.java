package pl.timsixth.thetag.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.timsixth.gui.libray.model.Menu;
import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.GameManager;
import pl.timsixth.minigameapi.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.stats.model.UserStatsDbModel;
import pl.timsixth.minigameapi.util.ChatUtil;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TheTagCommand implements CommandExecutor {

    private final ArenaManager<ArenaFileModel> arenaManager;
    private final GameManager gameManager;
    private final Messages messages;
    private final UserStatsManager<UserStatsDbModel> statisticsManager;
    private final GameLogic gameLogic;
    private final UserCoinsManager<UserCoinsDbModel> userCoinsManager;
    private final MenuManager menuManager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("Only players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendMessageWithPlayerHelp(player);
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("leave")) {
                Optional<Game> gameOptional = gameManager.getGameByPlayer(player);
                if (!gameOptional.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getNotCurrentlyPlaying());
                    return true;
                }
                Game game = gameOptional.get();

                gameManager.leaveFromGame(game, player);
                PlayerUtil.sendMessage(player, messages.getLeftPlayer());

            } else if (args[0].equalsIgnoreCase("list")) {
                List<ArenaFileModel> arenas = arenaManager.getArenas();
                if (arenas.isEmpty()) {
                    PlayerUtil.sendMessage(player, messages.getEmptyArenaList());
                    return true;
                }
                PlayerUtil.sendMessage(player, messages.getArenaList());
                List<String> arenasNames = arenas.stream()
                        .map(Arena::getName)
                        .collect(Collectors.toList());

                arenasNames.forEach(arenaName -> PlayerUtil.sendMessage(player, ChatUtil.chatColor(ChatUtil.chatColor("&e- " + arenaName))));

            } else if (args[0].equalsIgnoreCase("stats")) {
                Optional<UserCoinsDbModel> userCoinsDbModelOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
                if (!userCoinsDbModelOptional.isPresent()) return true;

                UserCoinsDbModel userCoinsDbModel = userCoinsDbModelOptional.get();

                for (String playerStat : messages.getPlayerStats()) {
                    PlayerUtil.sendMessage(player, ChatUtil.chatColor(playerStat
                            .replace("{PLAYER_DEFEATS}", String.valueOf(statisticsManager.getTotalDefeats(player.getUniqueId())))
                            .replace("{PLAYER_WINS}", String.valueOf(statisticsManager.getTotalWins(player.getUniqueId())))
                            .replace("{PLAYER_GAMES_PLAYED}", String.valueOf(statisticsManager.getTotalGamesPlayed(player.getUniqueId())))
                            .replace("{PLAYER_COINS}", String.valueOf(userCoinsDbModel.getCoins()))
                    ));
                }
            } else if (args[0].equalsIgnoreCase("randomjoin")) {
                gameLogic.randomJoin(player);
            } else if (args[0].equalsIgnoreCase("cosmeticshop")) {
                Optional<Menu> optionalMenu = menuManager.getMenuByName("main_cosmetics");

                if (!optionalMenu.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getMenuDoesntExists());
                    return true;
                }

                player.openInventory(menuManager.createMenu(optionalMenu.get()));
            } else {
                sendMessageWithPlayerHelp(player);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("join")) {
                Optional<ArenaFileModel> arenaOptional = arenaManager.getArena(args[1]);
                if (!arenaOptional.isPresent()) {
                    PlayerUtil.sendMessage(player, messages.getArenaDoesNotExists());
                    return true;
                }
                gameManager.joinGame(arenaOptional.get(), player);
            } else {
                sendMessageWithPlayerHelp(player);
            }
        }
        return false;
    }

    private void sendMessageWithPlayerHelp(Player player) {
        for (String message : messages.getPlayerHelp()) {
            PlayerUtil.sendMessage(player, ChatUtil.chatColor(message));
        }
    }
}
