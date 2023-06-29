package pl.timsixth.minigameapi.game;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.game.state.GameState;
import pl.timsixth.minigameapi.game.team.Team;
import pl.timsixth.minigameapi.game.user.UserGame;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents every game
 */
public interface Game {
    /**
     * Gets arena which is using to play this game
     *
     * @return arena which is using to play this game
     */
    Arena getArena();

    /**
     * @return list of all playing users
     */
    List<UserGame> getPlayingUsers();

    /**
     * @return current game state
     */
    GameState getState();

    /**
     * Sets new game state
     *
     * @param gameState new game state
     */
    void setState(GameState gameState);

    /**
     * Gets {@link UserGame} by name
     *
     * @param uuid user uuid
     * @return Optional of user
     */
    Optional<UserGame> getUserGame(UUID uuid);

    /**
     * Sends message to everyone who is playing on this game
     *
     * @param message the message
     */
    void sendMessage(String message);

    /**
     * @return amount of rounds
     */
    int getRounds();

    /**
     * Sets new rounds amount
     *
     * @param rounds new round amount
     */
    void setRounds(int rounds);

    /**
     * Adds one round to rounds
     */
    void addRound();

    /**
     * @return teams in game
     */
    List<Team> getTeams();

    /**
     * Gets team by name
     *
     * @param name team's name
     * @return Optional of team
     */
    Optional<Team> getTeamByName(String name);

    /**
     * Gets team by player
     *
     * @param player player to get team
     * @return Optional of team
     */
    Optional<Team> getTeamByPlayer(Player player);
}
