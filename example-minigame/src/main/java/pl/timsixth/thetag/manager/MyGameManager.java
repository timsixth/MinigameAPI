package pl.timsixth.thetag.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.impl.AbstractGameManager;
import pl.timsixth.minigameapi.api.game.impl.GameImpl;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.game.state.*;
import pl.timsixth.thetag.model.MyUserGame;
import pl.timsixth.thetag.util.ItemUtil;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyGameManager extends AbstractGameManager {

    private final Settings settings;
    private final TheTagPlugin theTagPlugin;
    private final Messages messages;
    private final ScoreboardManager scoreboardManager;
    private final UserStatsManager statisticsManager;
    private final GameLogic gameLogic;

    public MyGameManager(Settings settings, TheTagPlugin theTagPlugin, Messages messages, ScoreboardManager scoreboardManager, UserStatsManager statisticsManager, UserCosmeticsManager userCosmeticsManager) {
        this.settings = settings;
        this.theTagPlugin = theTagPlugin;
        this.messages = messages;
        this.scoreboardManager = scoreboardManager;
        this.statisticsManager = statisticsManager;

        gameLogic = new GameLogic(this, statisticsManager, messages, theTagPlugin.getArenaManager(), settings, userCosmeticsManager);
    }

    @Override
    public String randomGame() {
        if (getGames().isEmpty()) return theTagPlugin.getArenaManager().randomArenaName();

        List<Game> waitingGames = getGames().stream()
                .filter(game -> game.getState() instanceof WaitingGameState)
                .collect(Collectors.toList());

        for (Game game : waitingGames) {
            if (game.getPlayingUsers().size() == 1) {
                return game.getArena().getName();
            }
            return game.getArena().getName();
        }

        return theTagPlugin.getArenaManager().randomArenaName();
    }

    @Override
    public void joinGame(Arena arena, Player player) {
        if (getGameByPlayer(player).isPresent()) {
            PlayerUtil.sendMessage(player, messages.getGameAlreadyJoined());
            return;
        }

        Optional<Game> gameOptional = getGameByArenaName(arena.getName());
        if (!gameOptional.isPresent()) {
            Game game = newGame(player, arena);

            player.teleport(arena.getLobbyLocation());

            game.setState(new WaitingGameState(settings, messages, game, theTagPlugin, scoreboardManager, gameLogic, statisticsManager));
            game.sendMessage(messages.getGameJoined().replace("{NICK}", player.getName()));
            player.getInventory().clear();
            giveLeaveItem(player);
        } else {
            Game game = gameOptional.get();
            if (game.getState() instanceof WaitingGameState) {
                UserGame userGame = new MyUserGame(player.getUniqueId());

                game.getPlayingUsers().add(userGame);

                player.teleport(arena.getLobbyLocation());

                game.getState().run();
                game.sendMessage(messages.getGameJoined().replace("{NICK}", player.getName()));
                player.getInventory().clear();
                giveLeaveItem(player);
            } else if (game.getState() instanceof StartingGameState) {
                if (game.getPlayingUsers().size() == settings.getMaxPlayers()) {
                    PlayerUtil.sendMessage(player, messages.getArenaCurrentlyPlaying());
                    return;
                }

                UserGame userGame = new MyUserGame(player.getUniqueId());

                game.getPlayingUsers().add(userGame);

                player.teleport(arena.getLobbyLocation());

                game.sendMessage(messages.getGameJoined().replace("{NICK}", player.getName()));
                player.getInventory().clear();
                giveLeaveItem(player);
            } else {
                PlayerUtil.sendMessage(player, messages.getArenaCurrentlyPlaying());
            }
        }
    }

    @Override
    public void leaveFromGame(Game game, Player player) {
        Optional<UserGame> userGameOptional = game.getUserGame(player.getUniqueId());
        if (!userGameOptional.isPresent()) return;

        UserGame userGame = userGameOptional.get();
        game.getPlayingUsers().remove(userGame);

        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        ItemUtil.clearPlayerInventory(player);
        player.getActivePotionEffects().clear();
        player.teleport(settings.getLobbyLocation());

        game.sendMessage(messages.getGameLeft().replace("{NICK}", player.getName()));

        if (game.getState() instanceof StartingGameState) {
            game.setState(new WaitingGameState(settings, messages, game, theTagPlugin, scoreboardManager, gameLogic, statisticsManager));
        } else if (game.getState() instanceof PlayingGameState || game.getState() instanceof DrawingTheTagGameState
                || game.getState() instanceof FinalGameState) {
            userGame.setPlaying(false);
            if (game.getPlayingUsers().size() == 2) {
                game.setState(new FinalGameState(game, theTagPlugin, messages, settings, scoreboardManager, statisticsManager, gameLogic));
            } else if (game.getPlayingUsers().size() == 1) {
                game.setState(new WinGameState(game, theTagPlugin, messages, statisticsManager, settings, gameLogic));
            } else if (game.getPlayingUsers().isEmpty()) {
                game.setState(new RestartingGameState(this, game));
            }
        }
    }

    private Game newGame(Player player, Arena area) {
        Game game = new GameImpl(area);

        UserGame userGame = new MyUserGame(player.getUniqueId());

        game.getPlayingUsers().add(userGame);

        getGames().add(game);

        return game;
    }

    private void giveLeaveItem(Player player) {
        ItemStack item = settings.leaveItem();
        player.getInventory().setItem(8, item);
    }

    @Override
    protected GameState getRestartingGameState(Game game) {
        return new RestartingGameState(this, game);
    }
}
