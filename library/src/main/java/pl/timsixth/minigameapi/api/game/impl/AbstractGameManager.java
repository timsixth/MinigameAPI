package pl.timsixth.minigameapi.api.game.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.user.UserGame;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Template method fo {@link GameManager}
 */
public abstract class AbstractGameManager implements GameManager {
    @Getter
    private final Set<Game> games = new HashSet<>();

    @Override
    public Optional<Game> getGameByPlayer(Player player) {
        for (Game game : games) {
            for (UserGame playingUser : game.getPlayingUsers()) {
                if (playingUser.getUuid().equals(player.getUniqueId())) {
                    return Optional.of(game);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Game> getGameByArenaName(String arenaName) {
        return games.stream()
                .filter(game -> game.getArena().getName().equalsIgnoreCase(arenaName))
                .findAny();
    }
}
