package pl.timsixth.minigameapi.api.game.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.user.rejoin.RejoinState;
import pl.timsixth.minigameapi.api.game.state.GameState;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.UserGame;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Template method fo {@link GameManager}
 */
@Getter
public abstract class AbstractGameManager implements GameManager {
    protected final Set<Game> games = new HashSet<>();

    @Override
    public Optional<Game> getGameByPlayer(Player player) {
        return games.stream()
                .filter(game -> game.getPlayingUsers().stream()
                        .anyMatch(userGame -> userGame.getUuid().equals(player.getUniqueId())))
                .findAny();
    }

    @Override
    public Optional<Game> getGameByArenaName(String arenaName) {
        return games.stream()
                .filter(game -> game.getArena().getName().equalsIgnoreCase(arenaName))
                .findAny();
    }

    @Override
    public void gameRestart(Game game) {
        game.setState(getRestartingGameState(game));
    }

    /**
     * Gets restarting game state, override this method if you want to use default restarting system
     *
     * @param game game to restart
     * @return restarting game state
     */
    protected GameState getRestartingGameState(Game game) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addGame(Game game) {
        this.games.add(game);
    }

    @Override
    public void removeGame(Game game) {
        this.games.remove(game);
    }

    @Override
    public Optional<Game> rejoin(Player player, Function<Game, Boolean> canRejoinFunction) {
        return rejoin(player, canRejoinFunction, (rejoinState) -> {
            RecoverableUserGame recoverableUserGame = rejoinState.getRecoverableUserGame();
            Game game = rejoinState.getGame();

            UserGame userGame = getUserGameConverter().convertToUserGame(recoverableUserGame);

            game.addUserGame(userGame);

            rejoinState.restorePlayerOptions(player);
            rejoinState.restorePlayerInventory(player);

            game.removeRecoverableUserGame(recoverableUserGame);
        });
    }

    @Override
    public Optional<Game> rejoin(Player player, Function<Game, Boolean> canRejoinFunction, Consumer<RejoinState> rejoinConsumer) {
        Optional<Result> resultOptional = getOldUserGame(player);
        if (!resultOptional.isPresent()) return Optional.empty();
        Result result = resultOptional.get();

        Game game = result.getGame();
        if (!canRejoinFunction.apply(game)) return Optional.empty();

        RecoverableUserGame recoverableUserGame = result.getRecoverableUserGame();

        rejoinConsumer.accept(new RejoinState(game, recoverableUserGame));

        return Optional.of(game);
    }


    private Optional<Result> getOldUserGame(Player player) {
        for (Game game : games) {
            Optional<RecoverableUserGame> oldUserGameOptional = game.getRecoverableUserGame(player.getUniqueId());

            if (oldUserGameOptional.isPresent()) {
                return Optional.of(new Result(game, oldUserGameOptional.get()));
            }
        }
        return Optional.empty();
    }

    @RequiredArgsConstructor
    @Getter
    private static class Result {
        private final Game game;
        private final RecoverableUserGame recoverableUserGame;
    }
}
