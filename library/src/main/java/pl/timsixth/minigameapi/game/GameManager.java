package pl.timsixth.minigameapi.game;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.Arena;

import java.util.Optional;
import java.util.Set;

public interface GameManager {

    Optional<Game> getGameByPlayer(Player player);

    Optional<Game> getGameByArenaName(String arenaName);

    String randomGame();

    void gameRestart(Game game);

    void joinGame(Arena arena, Player player);

    void leaveFromGame(Game game, Player player);

    Set<Game> getGames();
}
