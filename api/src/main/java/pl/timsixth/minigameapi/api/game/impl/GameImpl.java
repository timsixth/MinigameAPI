package pl.timsixth.minigameapi.api.game.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.team.Team;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.UserGame;

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
    private List<Team> teams;
    private List<RecoverableUserGame> recoverableUserGames;

    public GameImpl(Arena arena) {
        this.arena = arena;
        this.playingUsers = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.recoverableUserGames = new ArrayList<>();
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

            if (player == null) continue;

            player.sendMessage(message);
        }
    }

    @Override
    public void addRound() {
        rounds++;
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teams.stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public Optional<Team> getTeamByPlayer(Player player) {
        for (Team team : teams) {
            for (UserGame user : team.getUsers()) {
                if (user.getUuid().equals(player.getUniqueId())) {
                    return Optional.of(team);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void addUserGame(UserGame userGame) {
        this.playingUsers.add(userGame);
    }

    @Override
    public void removeUserGame(UserGame userGame) {
        this.playingUsers.remove(userGame);
    }

    @Override
    public void addRecoverableUserGame(RecoverableUserGame recoverableUserGame) {
        this.recoverableUserGames.add(recoverableUserGame);
    }

    @Override
    public void removeRecoverableUserGame(RecoverableUserGame recoverableUserGame) {
        this.recoverableUserGames.remove(recoverableUserGame);
    }

    @Override
    public Optional<RecoverableUserGame> getRecoverableUserGame(UUID playerUuid) {
        return this.recoverableUserGames.stream()
                .filter(oldUserGame -> oldUserGame.getUuid().equals(playerUuid))
                .findAny();
    }
}
