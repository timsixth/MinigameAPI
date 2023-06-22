package pl.timsixth.minigameapi.game.impl;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.GameManager;
import pl.timsixth.minigameapi.game.user.UserGame;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameManagerImpl implements GameManager {

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
