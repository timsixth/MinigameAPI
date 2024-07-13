package pl.timsixth.minigameapi.api.module.sql.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.sql.SQLModule;
import pl.timsixth.minigameapi.api.module.sql.coins.factory.SQLDatabaseUserCoinsFactory;
import pl.timsixth.minigameapi.api.module.sql.coins.loader.UserCoinsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.factory.SQLDatabaseUserCosmeticsFactory;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.loader.UserCosmeticsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.module.sql.stats.factory.SQLDatabaseUserStatsFactory;
import pl.timsixth.minigameapi.api.module.sql.stats.loader.factory.SQLDatabaseUserStatsLoaderFactory;

import java.util.List;

/**
 * Presents library configuration for SQL module
 */
public class SQLLibraryConfiguration extends LibraryConfiguration {

    public SQLLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer) {
        super(plugin, configuratorsInitializer);
    }

    public SQLLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, List<Module> modules) {
        super(plugin, configuratorsInitializer, modules);
    }

    private SQLLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, SQLLibraryConfiguration.Builder builder) {
        super(plugin, configuratorsInitializer, builder);
    }

    @Override
    public SQLLibraryConfiguration.Builder builder() {
        return new Builder(getPlugin(), getConfiguratorsInitializer(), getModules());
    }

    public static class Builder extends LibraryConfiguration.Builder {

        public Builder(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, List<Module> modules) {
            super(plugin, configuratorsInitializer, modules);

            SQLModule sqlModule = (SQLModule) checkBeforeRegistration("minigameapi-sql");
            SQLDatabaseAdapter sqlDatabaseAdapter = sqlModule.getSqlModuleConfiguration().getSqlDatabaseAdapter();

            setUserCoinsFactory(new SQLDatabaseUserCoinsFactory());
            setUserCoinsLoader(new UserCoinsSQLDatabaseLoader(sqlDatabaseAdapter));

            if (configuratorsInitializer.getPluginConfiguration().isUseDefaultStatsSystem())
                setUserStatsFactory(new SQLDatabaseUserStatsFactory());
            setUserStatsLoaderFactory(new SQLDatabaseUserStatsLoaderFactory(sqlDatabaseAdapter));

            setUserCosmeticsFactory(new SQLDatabaseUserCosmeticsFactory());
            setUserCosmeticsLoader(new UserCosmeticsSQLDatabaseLoader(sqlDatabaseAdapter, cosmeticsManager));
        }

        @Override
        public SQLLibraryConfiguration build() {
            super.build();
            return new SQLLibraryConfiguration(plugin, configuratorsInitializer, this);
        }
    }
}
