package pl.timsixth.thetag.task;

import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.game.state.DrawingTheTagGameState;
import pl.timsixth.thetag.manager.ScoreboardManager;

@AllArgsConstructor
public class StartGameCountDown extends BukkitRunnable {

    private final Game game;
    private final Settings settings;
    private final TheTagPlugin theTagPlugin;
    private final Messages messages;
    private final ScoreboardManager scoreboardManager;
    private final GameLogic gameLogic;
    private final UserStatsManager statisticsManager;
    private int gameTimer;

    @Override
    public void run() {
        if (gameTimer > 0) {
            game.sendMessage(messages.getGameStarting() + gameTimer);
            gameTimer--;
            if (gameTimer == 0) {
                game.setRounds(1);
                game.setState(new DrawingTheTagGameState(gameLogic, game, theTagPlugin, settings, messages, scoreboardManager, statisticsManager));

                cancel();
                gameTimer = settings.getStartTimer();
            }
            if (game.getPlayingUsers().size() < settings.getMinPlayers()) {
                game.sendMessage(messages.getStartingCanceled());

                cancel();
                gameTimer = settings.getStartTimer();
            }
        }
    }
}
