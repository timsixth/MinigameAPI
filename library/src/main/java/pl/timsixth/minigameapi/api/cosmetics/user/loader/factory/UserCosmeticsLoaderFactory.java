package pl.timsixth.minigameapi.api.cosmetics.user.loader.factory;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
@RequiredArgsConstructor
public class UserCosmeticsLoaderFactory implements LoaderFactory<UserCosmetics> {

    private final CosmeticsManager cosmeticsManager;

    @Override
    public Loader<UserCosmetics> createLoader() {
        if (MiniGame.getInstance().getPluginConfiguration().isUseDataBase())
            return new UserCosmeticsSQLDatabaseLoader(cosmeticsManager);

        return new UserCosmeticsSQLDatabaseLoader(cosmeticsManager);
    }
}
