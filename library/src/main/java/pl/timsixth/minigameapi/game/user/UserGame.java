package pl.timsixth.minigameapi.game.user;

import pl.timsixth.minigameapi.user.User;

public interface UserGame extends User {

    int getPoints();

    int addPoints(int points);

    int removePoints(int points);

    void setPoints(int points);

    boolean isPlaying();

    void setPlaying(boolean playing);
}
