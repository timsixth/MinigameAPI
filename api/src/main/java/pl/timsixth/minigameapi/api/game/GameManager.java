package pl.timsixth.minigameapi.api.game;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;

import java.util.Optional;
import java.util.Set;

public interface GameManager {
    /**
     * Gets game by player
     *
     * @param player player who is playing on this game
     * @return optional of game
     */
    Optional<Game> getGameByPlayer(Player player);

    /**
     * Gets game by arena name
     *
     * @param arenaName arena name
     * @return optional of game
     */
    Optional<Game> getGameByArenaName(String arenaName);

    /**
     * Draws arena name
     *
     * @return random arena name
     */
    String randomGame();

    /**
     * Restart game
     *
     * @param game game to restart
     */
    void gameRestart(Game game);

    /**
     * Joins player to arena
     *
     * @param arena  arena to join
     * @param player player who want to join a new game
     */
    void joinGame(Arena arena, Player player);

    /**
     * Leaves player from game
     *
     * @param game   game to leave
     * @param player player who want to leave from game
     */
    void leaveFromGame(Game game, Player player);

    /**
     * @return set of games
     */
    Set<Game> getGames();

    /**
     * Adds game
     *
     * @param game game to add
     */
    void addGame(Game game);
}
