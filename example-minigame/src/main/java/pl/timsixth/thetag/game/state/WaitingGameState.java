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

@RequiredArgsConstructor
public class WaitingGameState implements GameState {

    private final Settings settings;
    private final Messages messages;
    private final Game game;
    private final TheTagPlugin theTagPlugin;
    private final ScoreboardManager scoreboardManager;
    private final GameLogic gameLogic;
    private final UserStatsManager statisticsManager;

    @Override
    public void run() {
        if (game.getPlayingUsers().size() >= settings.getMinPlayers() && game.getPlayingUsers().size() <= settings.getMaxPlayers()) {
            game.setState(new StartingGameState(theTagPlugin, settings, messages, game, scoreboardManager, gameLogic, statisticsManager));
        }
    }
}
