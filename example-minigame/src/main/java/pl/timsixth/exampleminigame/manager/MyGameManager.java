package pl.timsixth.exampleminigame.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.exampleminigame.ExampleMiniGamePlugin;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.gamestate.*;
import pl.timsixth.exampleminigame.model.MyUserGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.impl.AbstractGameManager;
import pl.timsixth.minigameapi.api.game.impl.GameImpl;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MyGameManager extends AbstractGameManager {

    private final ExampleMiniGamePlugin plugin;
    private final Settings settings;
    private final Messages messages;

    @Override
    public String randomGame() {
        if (getGames().isEmpty()) return plugin.getArenaManager().randomArenaName();

        List<Game> waitingGames = getGames().stream()
                .filter(game -> game.getState() instanceof WaitingGameState)
                .collect(Collectors.toList());

        for (Game game : waitingGames) {
            if (game.getPlayingUsers().size() == 1) {
                return game.getArena().getName();
            }
            return game.getArena().getName();
        }

        return plugin.getArenaManager().randomArenaName();
    }

    @Override
    public void joinGame(Arena arena, Player player) {
        if (getGameByPlayer(player).isPresent()) {
            player.sendMessage(messages.getGameAlreadyJoined());
            return;
        }

        Optional<Game> gameOptional = getGameByArenaName(arena.getName());
        if (!gameOptional.isPresent()) {
            Game game = newGame(player, arena);

            player.teleport(arena.getLobbyLocation());

            game.setState(new WaitingGameState(game, settings, plugin, messages));
            game.sendMessage(messages.getGameJoined().replace("{NICK}", player.getName()));
            player.getInventory().clear();
            giveLeaveItem(player);
        } else {
            Game game = gameOptional.get();
            if (game.getState() instanceof WaitingGameState) {
                UserGame userGame = new MyUserGame(player.getUniqueId());

                game.addUserGame(userGame);

                player.teleport(arena.getLobbyLocation());

                game.runState();
                game.sendMessage(messages.getGameJoined().replace("{NICK}", player.getName()));
                player.getInventory().clear();
                giveLeaveItem(player);
            } else if (game.getState() instanceof StartingGameState) {
                if (game.getPlayingUsers().size() == settings.getMaxPlayers()) {
                    player.sendMessage(messages.getArenaCurrentlyPlaying());
                    return;
                }

                UserGame userGame = new MyUserGame(player.getUniqueId());

                game.addUserGame(userGame);

                player.teleport(arena.getLobbyLocation());

                game.sendMessage(messages.getGameJoined().replace("{NICK}", player.getName()));
                player.getInventory().clear();
                giveLeaveItem(player);
            } else {
                player.sendMessage(messages.getArenaCurrentlyPlaying());
            }
        }
    }

    @Override
    public void leaveFromGame(Game game, Player player) {
        Optional<UserGame> userGameOptional = game.getUserGame(player.getUniqueId());
        if (!userGameOptional.isPresent()) return;

        UserGame userGame = userGameOptional.get();
        game.removeUserGame(userGame);

        //Store the player to set up the rejoin feature. Remember to do this when a player has a location in the game and items from the game
        if (game.getState() instanceof PlayingGameState) {
            MyUserGame myUserGame = (MyUserGame) userGame;
            RecoverableUserGame recoverableUserGame = getUserGameConverter().convertFromUserGame(myUserGame);

            game.addRecoverableUserGame(recoverableUserGame);
        }

        player.getInventory().clear();

        player.teleport(settings.getLobbyLocation());

        game.sendMessage(messages.getGameLeft().replace("{NICK}", player.getName()));

        if (game.getState() instanceof StartingGameState) {
            game.setState(new WaitingGameState(game, settings, plugin, messages));
        } else if (game.getState() instanceof PlayingGameState) {
            userGame.setPlaying(false);

            if (game.getPlayingUsers().size() == 1) {
                UserStatsManager userStatsManager = plugin.getUserStatsManager();

                UserStats userStats = userStatsManager.getUserStatsOrCreate(player, game.getArena());
                userStats.addDefeat();

                game.setState(new WinGameState(game, messages, userStatsManager, settings, plugin.getGameManager(),
                        plugin.getUserCoinsManager()));
            } else if (game.getPlayingUsers().isEmpty()) {
                game.setState(new RestartingGameState(this, game));
            }
        }
    }

    //Sets game state to restarting game, gameRestart can be overridden.
    @Override
    protected GameState getRestartingGameState(Game game) {
        return new RestartingGameState(this, game);
    }

    private Game newGame(Player player, Arena area) {
        Game game = new GameImpl(area);

        UserGame userGame = new MyUserGame(player.getUniqueId());

        game.addUserGame(userGame);

        addGame(game);

        return game;
    }

    private void giveLeaveItem(Player player) {
        ItemStack item = settings.leaveItem();
        player.getInventory().setItem(8, item);
    }
}
