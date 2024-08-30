package pl.timsixth.minigameapi.api.module.mongodb.core.configuration;

import org.bukkit.plugin.Plugin;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManagerImpl;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.mongodb.coins.factory.MongoDbUserCoinsFactory;
import pl.timsixth.minigameapi.api.module.mongodb.coins.loader.MongoDbUserCoinsLoader;

import java.util.List;
import java.util.function.Supplier;

public class MongoDbLibraryConfiguration extends LibraryConfiguration {

    public MongoDbLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer) {
        super(plugin, configuratorsInitializer);
    }

    public MongoDbLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, Supplier<List<Module>> modulesSupplier) {
        super(plugin, configuratorsInitializer, modulesSupplier);
    }

    private MongoDbLibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, Builder builder) {
        super(plugin, configuratorsInitializer, builder);
    }

    @Override
    public MongoDbLibraryConfiguration.Builder builder() {
        return new Builder(getPlugin(), getConfiguratorsInitializer(), getModules());
    }

    public static class Builder extends LibraryConfiguration.Builder {

        public Builder(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, List<Module> modules) {
            super(plugin, configuratorsInitializer, modules);

            UserCoinsLoader userCoinsLoader = new MongoDbUserCoinsLoader();

            setUserCoinsFactory(new MongoDbUserCoinsFactory());
            setUserCoinsLoader(userCoinsLoader);
            setUserCoinsManager(new UserCoinsManagerImpl(userCoinsLoader));

        }

        @Override
        public MongoDbLibraryConfiguration build() {
            super.build();
            return new MongoDbLibraryConfiguration(plugin, configuratorsInitializer, this);
        }
    }
}
