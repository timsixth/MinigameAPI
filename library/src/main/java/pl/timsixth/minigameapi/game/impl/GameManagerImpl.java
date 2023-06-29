package pl.timsixth.minigameapi.game.impl;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.GameManager;

/**
 * Default implementation of {@link GameManager}
 */
public class GameManagerImpl extends AbstractGameManager {

    @Override
    public String randomGame() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void gameRestart(Game game) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void joinGame(Arena arena, Player player) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void leaveFromGame(Game game, Player player) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
