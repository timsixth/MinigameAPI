package pl.timsixth.thetag.game.state;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.GameManager;
import pl.timsixth.minigameapi.game.state.GameState;

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
