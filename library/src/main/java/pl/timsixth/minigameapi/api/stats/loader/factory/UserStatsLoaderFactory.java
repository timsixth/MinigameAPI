package pl.timsixth.minigameapi.api.stats.loader.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

public class UserStatsLoaderFactory implements LoaderFactory<UserStats> {
    @Override
    public Loader<UserStats> createLoader() {
        PluginConfiguration pluginConfiguration = MiniGame.getInstance().getPluginConfiguration();

        if (pluginConfiguration.isUseDefaultStatsSystem()) return new UserStatsSQLDatabaseLoader();

        if (pluginConfiguration.isUseDataBase()) return new UserStatsSQLDatabaseLoader();

        return new UserStatsSQLDatabaseLoader();
    }
}
