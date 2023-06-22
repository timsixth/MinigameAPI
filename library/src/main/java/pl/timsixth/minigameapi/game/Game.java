package pl.timsixth.minigameapi.game;

import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.game.state.GameState;
import pl.timsixth.minigameapi.game.user.UserGame;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Game {

    Arena getArena();

    List<UserGame> getPlayingUsers();

    GameState getState();

    void setState(GameState gameState);

    Optional<UserGame> getUserGame(UUID uuid);

    /**
     * Sends message to everyone who is playing on this game
     *
     * @param message the message
     */
    void sendMessage(String message);

    int getRounds();

    void setRounds(int rounds);

    void addRound();
}
