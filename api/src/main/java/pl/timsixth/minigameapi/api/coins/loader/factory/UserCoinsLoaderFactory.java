package pl.timsixth.minigameapi.api.coins.loader.factory;

import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsSingleFileLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;

@Deprecated
public class UserCoinsLoaderFactory implements LoaderFactory<UserCoins> {
    @Override
    public Loader<UserCoins> createLoader() {
        return new UserCoinsSingleFileLoader();
    }
}
