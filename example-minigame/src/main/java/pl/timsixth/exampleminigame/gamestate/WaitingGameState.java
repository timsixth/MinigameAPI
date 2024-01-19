package pl.timsixth.exampleminigame.gamestate;

import lombok.RequiredArgsConstructor;
import pl.timsixth.exampleminigame.ExampleMiniGamePlugin;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.state.GameState;

@RequiredArgsConstructor
public class WaitingGameState implements GameState {

    private final Game game;
    private final Settings settings;
    private final ExampleMiniGamePlugin exampleMiniGamePlugin;
    private final Messages messages;

    @Override
    public void run() {
        if (game.getPlayingUsers().size() >= settings.getMinPlayers() && game.getPlayingUsers().size() <= settings.getMaxPlayers()) {
            game.setState(new StartingGameState(settings, messages, exampleMiniGamePlugin, game));
        }
    }
}
