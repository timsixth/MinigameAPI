package pl.timsixth.minigameapi.game.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.Arena;
import pl.timsixth.minigameapi.game.Game;
import pl.timsixth.minigameapi.game.state.GameState;
import pl.timsixth.minigameapi.game.user.UserGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link Game}
 */
@Getter
@Setter
@ToString
public class GameImpl implements Game {

    private final Arena arena;
    private List<UserGame> playingUsers;
    private GameState state;
    private int rounds;

    public GameImpl(Arena arena) {
        this.arena = arena;
        playingUsers = new ArrayList<>();
    }

    @Override
    public void setState(GameState gameState) {
        this.state = gameState;
        gameState.run();
    }

    @Override
    public Optional<UserGame> getUserGame(UUID uuid) {
        return playingUsers.stream()
                .filter(userGame -> userGame.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public void sendMessage(String message) {
        for (UserGame playingUser : playingUsers) {
            Player player = playingUser.toPlayer();
            player.sendMessage(message);
        }
    }

    @Override
    public void addRound() {
        rounds++;
    }
}
