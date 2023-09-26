package pl.timsixth.thetag.task;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.game.state.WinGameState;
import pl.timsixth.thetag.manager.ScoreboardManager;

@AllArgsConstructor
public class StartFinalCountDown extends BukkitRunnable {

    private final TheTagPlugin theTagPlugin;
    private final Game game;
    private final Messages messages;
    private final Settings settings;
    private final ScoreboardManager scoreboardManager;
    private final UserStatsManager statisticsManager;
    private final GameLogic gameLogic;
    private int gameTimer;

    @Override
    public void run() {
        if (gameTimer > 0) {
            gameTimer--;
            for (UserGame playingUser : game.getPlayingUsers()) {
                Player player = playingUser.toPlayer();

                scoreboardManager.showScoreboard(player, game, gameTimer);
            }
            if (gameTimer == 0) {

                gameLogic.checkTheTag(game);

                game.setState(new WinGameState(game, theTagPlugin, messages, statisticsManager,settings,gameLogic));

                cancel();
                gameTimer = settings.getFinalTimer();
            }
            if (game.getPlayingUsers().size() == 1) {
                game.setState(new WinGameState(game, theTagPlugin, messages, statisticsManager,settings,gameLogic));

                cancel();
                gameTimer = settings.getFinalTimer();
            }
        }
    }
}
