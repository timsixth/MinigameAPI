package pl.timsixth.minigameapi.api.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.team.Team;
import pl.timsixth.minigameapi.api.game.team.TeamImpl;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.game.user.UserGameImpl;

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

    /**
     * Adds new user game to playing players
     *
     * @param userGame user game to add
     */
    void addUserGame(UserGame userGame);

    /**
     * Removes user game from playing players
     *
     * @param userGame user game to remove
     */
    void removeUserGame(UserGame userGame);

    /**
     * Runes current game state
     */
    default void runState() {
        getState().run();
    }

    /**
     * Creates new user game, with default implementation
     *
     * @param playerUUID player's uuid
     * @return new user game
     */
    default UserGame createUserGame(UUID playerUUID) {
        return new UserGameImpl(playerUUID);
    }

    /**
     * Creates new team, with default implementation
     *
     * @param name        team name
     * @param displayName team display name
     * @param color       team color
     * @return new team
     */
    default Team createTeam(String name, String displayName, ChatColor color) {
        return new TeamImpl(name, displayName, color);
    }

    /**
     * Added new recoverableUserGame
     *
     * @param recoverableUserGame user to add
     */
    void addRecoverableUserGame(RecoverableUserGame recoverableUserGame);

    /**
     * Removes recoverableUserGame
     *
     * @param recoverableUserGame user to remove
     */
    void removeRecoverableUserGame(RecoverableUserGame recoverableUserGame);

    /**
     * Gets user by uuid
     *
     * @param playerUuid user's uuid
     * @return optional of RecoverableUserGame
     */
    Optional<RecoverableUserGame> getRecoverableUserGame(UUID playerUuid);

    /**
     * Gets list of RecoverableUserGames
     *
     * @return list of RecoverableUserGames
     */
    List<RecoverableUserGame> getRecoverableUserGames();
}
