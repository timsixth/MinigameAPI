package pl.timsixth.minigameapi.api.game.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.state.GameState;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Template method fo {@link GameManager}
 */
@Getter
public abstract class AbstractGameManager implements GameManager {
    protected final Set<Game> games = new HashSet<>();

    @Override
    public Optional<Game> getGameByPlayer(Player player) {
        return games.stream()
                .filter(game -> game.getPlayingUsers().stream()
                        .anyMatch(userGame -> userGame.getUuid().equals(player.getUniqueId())))
                .findAny();
    }

    @Override
    public Optional<Game> getGameByArenaName(String arenaName) {
        return games.stream()
                .filter(game -> game.getArena().getName().equalsIgnoreCase(arenaName))
                .findAny();
    }

    @Override
    public void gameRestart(Game game) {
        game.setState(getRestartingGameState(game));
    }

    /**
     * Gets restarting game state, override this method if you want to use default restarting system
     *
     * @param game game to restart
     * @return restarting game state
     */
    protected GameState getRestartingGameState(Game game) {
        throw new UnsupportedOperationException();
    }

}
