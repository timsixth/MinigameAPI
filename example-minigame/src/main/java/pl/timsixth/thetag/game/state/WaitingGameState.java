package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.state.GameState;
import pl.timsixth.minigameapi.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.stats.model.UserStatsDbModel;
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
    private final UserStatsManager<UserStatsDbModel> statisticsManager;

    @Override
    public void run() {
        if (game.getPlayingUsers().size() >= settings.getMinPlayers() && game.getPlayingUsers().size() <= settings.getMaxPlayers()) {
            game.setState(new StartingGameState(theTagPlugin, settings, messages, game, scoreboardManager, gameLogic, statisticsManager));
        }
    }
}
