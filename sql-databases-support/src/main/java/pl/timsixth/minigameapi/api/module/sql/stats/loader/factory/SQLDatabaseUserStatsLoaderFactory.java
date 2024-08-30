package pl.timsixth.minigameapi.api.module.sql.stats.loader.factory;

import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.logging.MiniGameLogger;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.stats.loader.UserStatsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

@Deprecated
@RequiredArgsConstructor
public class SQLDatabaseUserStatsLoaderFactory implements LoaderFactory<UserStats> {

    private final SQLDatabaseAdapter sqlDatabaseAdapter;

    @Override
    public Loader<UserStats> createLoader() {
        if (!MiniGame.getInstance().getPluginConfiguration().isUseDefaultStatsSystem()) {
            MiniGameLogger.info("Default stats system is disabled");
            return null;
        }

        return new UserStatsSQLDatabaseLoader(sqlDatabaseAdapter);
    }
}
