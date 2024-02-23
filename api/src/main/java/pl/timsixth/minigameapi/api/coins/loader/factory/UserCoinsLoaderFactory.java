package pl.timsixth.minigameapi.api.coins.loader.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsSingleFileLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;

public class UserCoinsLoaderFactory implements LoaderFactory<UserCoins> {
    @Override
    public Loader<UserCoins> createLoader() {
        if (MiniGame.getInstance().getPluginConfiguration().isUseDataBase())
            return new UserCoinsSQLDatabaseLoader();

        return new UserCoinsSingleFileLoader();
    }
}
