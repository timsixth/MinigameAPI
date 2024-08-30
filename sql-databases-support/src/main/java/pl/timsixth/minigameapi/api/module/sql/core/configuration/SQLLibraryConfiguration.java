package pl.timsixth.minigameapi.api.module.sql.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManagerImpl;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManagerImpl;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.sql.SQLModule;
import pl.timsixth.minigameapi.api.module.sql.coins.factory.SQLDatabaseUserCoinsFactory;
import pl.timsixth.minigameapi.api.module.sql.coins.loader.UserCoinsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.factory.SQLDatabaseUserCosmeticsFactory;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.loader.UserCosmeticsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.module.sql.stats.factory.SQLDatabaseUserStatsFactory;
import pl.timsixth.minigameapi.api.module.sql.stats.loader.UserStatsSQLDatabaseLoader;
import pl.timsixth.minigameapi.api.module.sql.stats.loader.factory.SQLDatabaseUserStatsLoaderFactory;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManagerImpl;

import java.util.List;
import java.util.function.Supplier;

/**
 * Presents library configuration for SQL module
 */
public class SQLLibraryConfiguration extends LibraryConfiguration {

    public SQLLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer) {
        super(plugin, configuratorsInitializer);
    }

    public SQLLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, Supplier<List<Module>> modulesSupplier) {
        super(plugin, configuratorsInitializer, modulesSupplier);
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

            UserCoinsLoader userCoinsLoader = new UserCoinsSQLDatabaseLoader(sqlDatabaseAdapter);

            setUserCoinsFactory(new SQLDatabaseUserCoinsFactory());
            setUserCoinsLoader(userCoinsLoader);
            setUserCoinsManager(new UserCoinsManagerImpl(userCoinsLoader));

            if (configuratorsInitializer.getPluginConfiguration().isUseDefaultStatsSystem()) {
                setUserStatsFactory(new SQLDatabaseUserStatsFactory());

                UserStatsLoader userStatsLoader = new UserStatsSQLDatabaseLoader(sqlDatabaseAdapter);
                setUserStatsLoader(userStatsLoader);
                setUserStatsManager(new UserStatsManagerImpl(userStatsLoader));
            }

            setUserCosmeticsFactory(new SQLDatabaseUserCosmeticsFactory());
            UserCosmeticsLoader userCosmeticsLoader = new UserCosmeticsSQLDatabaseLoader(sqlDatabaseAdapter, cosmeticsManager);
            setUserCosmeticsLoader(userCosmeticsLoader);
            setUserCosmeticsManager(new UserCosmeticsManagerImpl(userCosmeticsLoader));
        }

        @Override
        public SQLLibraryConfiguration build() {
            super.build();
            return new SQLLibraryConfiguration(plugin, configuratorsInitializer, this);
        }
    }
}
