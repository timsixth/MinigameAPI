package pl.timsixth.minigameapi.game.user;

import pl.timsixth.minigameapi.user.User;

/**
 * Represents every player which is playing in game
 */
public interface UserGame extends User {
    /**
     * @return user points
     */
    int getPoints();

    /**
     * Adds points
     *
     * @param points points to add
     * @return new amount of points
     */
    int addPoints(int points);

    /**
     * Removes points
     *
     * @param points points to remove
     * @return new amount of points
     */
    int removePoints(int points);

    /**
     * Sets points
     *
     * @param points points to set
     */
    void setPoints(int points);

    /**
     * @return true if player is playin otherwise false
     */
    boolean isPlaying();

    /**
     * Sets playing
     *
     * @param playing toggle playing true or false
     */
    void setPlaying(boolean playing);

    /**
     * @param points to check
     * @return true if player has points otherwise false
     */
    boolean hasPoints(int points);
}
