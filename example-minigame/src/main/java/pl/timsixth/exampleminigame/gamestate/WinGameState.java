package pl.timsixth.exampleminigame.gamestate;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.model.MyUserGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.event.PlayerWinGameEvent;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.Optional;

@RequiredArgsConstructor
public class WinGameState implements GameState {

    private final Game game;
    private final Messages messages;
    private final UserStatsManager statisticsManager;
    private final Settings settings;
    private final GameManager gameManager;
    private final UserCoinsManager userCoinsManager;

    @Override
    public void run() {
        UserGame winner = getWinner(game);

        for (UserGame playingUser : game.getPlayingUsers()) {
            playingUser.setPlaying(false);
            MyUserGame myUserGame = (MyUserGame) playingUser;
            Player player = playingUser.toPlayer();

            player.teleport(settings.getLobbyLocation());
            player.getInventory().clear();

            UserStats userStats = statisticsManager.getUserStatsOrCreate(player, game.getArena());

            if (winner.equals(myUserGame)) {
                winGame(player, userStats);
                continue;
            }

            //adds defeat for user's stats
            userStats.addDefeat();
        }

        gameManager.gameRestart(game);
    }

    private void winGame(Player player, UserStats userStats) {
        userStats.addWin();

        Optional<UserCoins> userCoinsOptional = userCoinsManager.getUserByUuid(player.getUniqueId());

        if (!userCoinsOptional.isPresent()) return;

        UserCoins userCoins = userCoinsOptional.get();

        userCoins.addCoins(settings.getCostOfWin());

        player.sendMessage(messages.getPlayerWon().replace("{NICK}", player.getName()));

        //this event call enables boosters addon in your minigame
        Bukkit.getPluginManager().callEvent(new PlayerWinGameEvent(player, game, settings.getCostOfWin()));
    }

    private UserGame getWinner(Game game) {
        for (UserGame playingUser : game.getPlayingUsers()) {
            MyUserGame myUserGame = (MyUserGame) playingUser;

            if (myUserGame.getBrokeBlocksAmount() == settings.getNeededBlockToWin()) {
                return myUserGame;
            }
        }

        return game.getPlayingUsers().get(0);
    }
}
