package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.state.GameState;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.manager.ScoreboardManager;
import pl.timsixth.thetag.manager.StatisticsManager;
import pl.timsixth.thetag.task.StartGameCountDown;

@RequiredArgsConstructor
public class StartingGameState implements GameState {

    private final TheTagPlugin theTagPlugin;
    private final Settings settings;
    private final Messages messages;
    private final Game game;
    private final ScoreboardManager scoreboardManager;
    private final GameLogic gameLogic;
    private final StatisticsManager statisticsManager;

    @Override
    public void run() {
        new StartGameCountDown(game, settings, theTagPlugin, messages, scoreboardManager,gameLogic,statisticsManager,settings.getStartTimer())
                .runTaskTimer(theTagPlugin, 20L, 20L);
    }
}
