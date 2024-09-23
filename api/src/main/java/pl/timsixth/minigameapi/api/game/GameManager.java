package pl.timsixth.minigameapi.api.game;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.game.impl.GameImpl;
import pl.timsixth.minigameapi.api.game.user.converter.UniversalUserGameConverter;
import pl.timsixth.minigameapi.api.game.user.converter.UserGameConverter;
import pl.timsixth.minigameapi.api.game.user.rejoin.RejoinState;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

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

    /**
     * Removes game
     *
     * @param game game to remove
     */
    void removeGame(Game game);

    /**
     * Creates new instance of game, with default implementation
     *
     * @param arena arena which on game must be started
     * @return created game instance
     */
    default Game craterGame(Arena arena) {
        return new GameImpl(arena);
    }

    /**
     * Gets converter to convert from playing user to not playing user, who can rejoin
     *
     * @return user game converter
     */
    default UserGameConverter getUserGameConverter() {
        return new UniversalUserGameConverter();
    }

    /**
     * Rejoins the latest arena
     *
     * @param player            player who wants to rejoin
     * @param canRejoinFunction condition that joins is allowed, maybe game state is still playing
     * @return optional of game, which player rejoined
     */
    Optional<Game> rejoin(Player player, Function<Game, Boolean> canRejoinFunction);

    /**
     * Rejoins the latest arena
     *
     * @param player            player who wants to rejoin
     * @param canRejoinFunction condition that joins is allowed, maybe game state is still playing
     * @param rejoinConsumer    function which is called when player has been joined to game
     * @return optional of game, which player rejoined
     */
    Optional<Game> rejoin(Player player, Function<Game, Boolean> canRejoinFunction, Consumer<RejoinState> rejoinConsumer);
}
