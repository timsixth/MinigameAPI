package pl.timsixth.minigameapi.api.game.user.converter;

import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGameImpl;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.game.user.UserGameImpl;
import pl.timsixth.minigameapi.api.util.options.CustomOptions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation fo {@link UserGameConverter}
 *
 * The converter stores other variables values in {@link CustomOptions#options()} container
 */
public class UniversalUserGameConverter implements UserGameConverter {

    @Override
    public <T extends UserGame> T convertToUserGame(RecoverableUserGame recoverableUserGame, Class<T> userGameClazz) {
        T userGame;

        try {
            userGame = userGameClazz.getConstructor(UUID.class).newInstance(recoverableUserGame.getUuid());

            userGame.setPlaying(true);
            userGame.setPoints(recoverableUserGame.getPoints());

            Field[] fields = userGameClazz.getDeclaredFields();

            Map<String, Object> options = recoverableUserGame.options().getOptions();

            if (options.isEmpty()) return userGame;

            for (Field field : fields) {
                field.setAccessible(true);

                if (!options.containsKey(field.getName())) continue;

                field.set(userGame, options.get(field.getName()));
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Can not convert from RecoverableUserGame to UserGame: Error" + e);
        }

        return userGame;
    }

    @Override
    public <T extends UserGame> RecoverableUserGame convertFromUserGame(T userGame) {
        RecoverableUserGame recoverableUserGame = new RecoverableUserGameImpl(userGame.toPlayer(), userGame.getPoints());

        Class<? extends UserGame> userGameClass = userGame.getClass();

        Field[] fields = userGameClass.getDeclaredFields();

        if (hasOnlyDefaultFields(fields)) return recoverableUserGame;

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                recoverableUserGame.options().setValue(field.getName(), field.get(userGame));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Can not convert from UserGame to RecoverableUserGame: Error" + e);
            }
        }

        return recoverableUserGame;
    }

    private boolean hasOnlyDefaultFields(Field[] fields) {
        return Arrays.equals(UserGameImpl.class.getDeclaredFields(), fields);
    }
}
