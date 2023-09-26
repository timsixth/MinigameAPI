package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.manager.ScoreboardManager;
import pl.timsixth.thetag.task.StartNextDrawingCountDown;

@RequiredArgsConstructor
public class PlayingGameState implements GameState {

    private final TheTagPlugin theTagPlugin;
    private final Settings settings;
    private final Messages messages;
    private final Game game;
    private final ScoreboardManager scoreboardManager;
    private final UserStatsManager statisticsManager;
    private final GameLogic gameLogic;

    @Override
    public void run() {
        new StartNextDrawingCountDown(game, settings, theTagPlugin, messages, scoreboardManager, statisticsManager, gameLogic, settings.getRoundTimer())
                .runTaskTimer(theTagPlugin, 20L, 20L);
    }
}
