package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.state.GameState;

@RequiredArgsConstructor
public class RestartingGameState implements GameState {

    private final GameManager gameManager;
    private final Game game;

    @Override
    public void run() {
        game.getPlayingUsers().clear();
        gameManager.getGames().remove(game);
    }
}
