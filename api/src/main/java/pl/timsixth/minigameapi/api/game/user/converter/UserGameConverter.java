package pl.timsixth.minigameapi.api.game.user.converter;

import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.game.user.UserGameImpl;

/**
 * Converter to converts UserGame to RecoverableUserGame
 */
public interface UserGameConverter {

    /**
     * Converts RecoverableUserGame to default UserGame implementation ({@link UserGameImpl})
     *
     * @param recoverableUserGame user to convert
     * @return UserGameImpl
     */
    default UserGame convertToUserGame(RecoverableUserGame recoverableUserGame) {
        return convertToUserGame(recoverableUserGame, UserGameImpl.class);
    }

    /**
     * @param recoverableUserGame user to convert
     * @param userGameClazz       the output class of conversion
     * @param <T>                 type which implemented UserGame
     * @return type which implemented UserGame
     */
    <T extends UserGame> T convertToUserGame(RecoverableUserGame recoverableUserGame, Class<T> userGameClazz);

    /**
     * @param userGame type which implemented UserGame
     * @param <T>      type which implemented UserGame
     * @return {@link RecoverableUserGame}
     */
    <T extends UserGame> RecoverableUserGame convertFromUserGame(T userGame);
}
