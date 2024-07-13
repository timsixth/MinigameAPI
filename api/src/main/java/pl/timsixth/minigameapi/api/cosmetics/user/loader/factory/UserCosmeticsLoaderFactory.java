package pl.timsixth.minigameapi.api.cosmetics.user.loader.factory;

import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsSingleFileLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;

/**
 * @deprecated From now this class is unnecessary
 */
@Deprecated
public class UserCosmeticsLoaderFactory implements LoaderFactory<UserCosmetics> {

    @Override
    public Loader<UserCosmetics> createLoader() {
        return new UserCosmeticsSingleFileLoader();
    }
}
