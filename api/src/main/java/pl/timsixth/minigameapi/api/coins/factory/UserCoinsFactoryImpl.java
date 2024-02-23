package pl.timsixth.minigameapi.api.coins.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.SQLDatabaseUserCoinsAdapter;
import pl.timsixth.minigameapi.api.coins.SingleFileUserCoinsAdapter;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.database.impl.DbModelImpl;
import pl.timsixth.minigameapi.api.file.impl.SingleFileModelImpl;

import java.util.UUID;

/**
 * Implementation of {@link UserCoinsFactory}
 */
public class UserCoinsFactoryImpl implements UserCoinsFactory {
    @Override
    public UserCoins createUserCoins(UUID uuid) {
        return createUserCoins(uuid, 0.0);
    }

    @Override
    public UserCoins createUserCoins(UUID uuid, double coins) {
        if (!MiniGame.getInstance().getPluginConfiguration().isUseDataBase())
            return new SingleFileUserCoinsAdapter(new SingleFileModelImpl(), uuid, coins);

        return new SQLDatabaseUserCoinsAdapter(new DbModelImpl(), uuid, coins);
    }

}
