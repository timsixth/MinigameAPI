package pl.timsixth.minigameapi.api.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactory;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactoryImpl;
import pl.timsixth.minigameapi.api.arena.loader.ArenaLoader;
import pl.timsixth.minigameapi.api.arena.loader.factory.ArenaLoaderFactory;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManagerImpl;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactory;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactoryImpl;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.coins.loader.factory.UserCoinsLoaderFactory;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManagerImpl;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.impl.CosmeticsManagerImpl;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactory;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactoryImpl;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.factory.UserCosmeticsLoaderFactory;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManagerImpl;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.impl.GameManagerImpl;
import pl.timsixth.minigameapi.api.listener.BlockBreakListener;
import pl.timsixth.minigameapi.api.listener.BlockPlaceListener;
import pl.timsixth.minigameapi.api.listener.PlayerDropItemListener;
import pl.timsixth.minigameapi.api.listener.PlayerJoinListener;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.ModuleManager;
import pl.timsixth.minigameapi.api.module.ModuleManagerImpl;
import pl.timsixth.minigameapi.api.module.exception.ModuleException;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactory;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactoryImpl;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.loader.factory.UserStatsLoaderFactory;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManagerImpl;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryConfiguration {

    private Plugin plugin;
    private ConfiguratorsInitializer configuratorsInitializer;
    private List<Module> modules;

    private ModuleManager moduleManager;
    private ArenaManager arenaManager;
    private UserCoinsManager userCoinsManager;
    private UserCosmeticsManager userCosmeticsManager;
    private UserStatsManager userStatsManager;
    private CosmeticsManager cosmeticsManager;
    private GameManager gameManager;

    private ArenaLoader arenaLoader;
    private UserCoinsLoader userCoinsLoader;
    private UserCosmeticsLoader userCosmeticsLoader;
    private UserStatsLoader userStatsLoader;

    private ArenaFactory arenaFactory;
    private UserCoinsFactory userCoinsFactory;
    private UserStatsFactory userStatsFactory;
    private UserCosmeticsFactory userCosmeticsFactory;

    private LoaderFactory<UserCoins> userCoinsLoaderFactory;
    private LoaderFactory<Arena> arenaLoaderFactory;
    private LoaderFactory<UserCosmetics> userCosmeticsLoaderFactory;
    private LoaderFactory<UserStats> userStatsLoaderFactory;

    protected LibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, Builder builder) {
        this.plugin = plugin;
        this.configuratorsInitializer = configuratorsInitializer;

        this.moduleManager = builder.moduleManager;
        this.arenaManager = builder.arenaManager;
        this.userCoinsManager = builder.userCoinsManager;
        this.userCosmeticsManager = builder.userCosmeticsManager;
        this.userStatsManager = builder.userStatsManager;
        this.cosmeticsManager = builder.cosmeticsManager;
        this.gameManager = builder.gameManager;

        this.arenaLoader = builder.arenaLoader;
        this.userCoinsLoader = builder.userCoinsLoader;
        this.userCosmeticsLoader = builder.userCosmeticsLoader;
        this.userStatsLoader = builder.userStatsLoader;

        this.arenaFactory = builder.arenaFactory;
        this.userCoinsFactory = builder.userCoinsFactory;
        this.userStatsFactory = builder.userStatsFactory;
        this.userCosmeticsFactory = builder.userCosmeticsFactory;

        this.userCoinsLoaderFactory = builder.userCoinsLoaderFactory;
        this.arenaLoaderFactory = builder.arenaLoaderFactory;
        this.userCosmeticsLoaderFactory = builder.userCosmeticsLoaderFactory;
        this.userStatsLoaderFactory = builder.userStatsLoaderFactory;
    }

    public LibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer) {
        this(plugin, configuratorsInitializer, Collections.emptyList());
    }

    public LibraryConfiguration(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, List<Module> modules) {
        this.plugin = plugin;
        this.configuratorsInitializer = configuratorsInitializer;
        this.modules = modules;
    }

    public Builder builder() {
        return new Builder(plugin, configuratorsInitializer, modules);
    }

    public static class Builder {

        protected final Plugin plugin;
        protected final ConfiguratorsInitializer configuratorsInitializer;

        protected final ModuleManager moduleManager;
        private ArenaManager arenaManager;
        private UserCoinsManager userCoinsManager;
        private UserCosmeticsManager userCosmeticsManager;
        private UserStatsManager userStatsManager;
        protected CosmeticsManager cosmeticsManager;
        private GameManager gameManager;

        private ArenaLoader arenaLoader;
        private UserCoinsLoader userCoinsLoader;
        private UserCosmeticsLoader userCosmeticsLoader;
        private UserStatsLoader userStatsLoader;

        private ArenaFactory arenaFactory;
        private UserCoinsFactory userCoinsFactory;
        private UserStatsFactory userStatsFactory;
        private UserCosmeticsFactory userCosmeticsFactory;

        private LoaderFactory<UserCoins> userCoinsLoaderFactory;
        private LoaderFactory<Arena> arenaLoaderFactory;
        private LoaderFactory<UserCosmetics> userCosmeticsLoaderFactory;
        private LoaderFactory<UserStats> userStatsLoaderFactory;

        public Builder(Plugin plugin, ConfiguratorsInitializer configuratorsInitializer, List<Module> modules) {
            this.plugin = plugin;
            this.configuratorsInitializer = configuratorsInitializer;

            this.moduleManager = new ModuleManagerImpl();

            registerModulesBeforeOtherModules(modules);

            this.cosmeticsManager = new CosmeticsManagerImpl();

            this.arenaFactory = new ArenaFactoryImpl();
            this.userCoinsFactory = new UserCoinsFactoryImpl();
            this.userCosmeticsFactory = new UserCosmeticsFactoryImpl();
            if (configuratorsInitializer.getPluginConfiguration().isUseDefaultStatsSystem())
                this.userStatsFactory = new UserStatsFactoryImpl();

            this.userCoinsLoaderFactory = new UserCoinsLoaderFactory();
            this.arenaLoaderFactory = new ArenaLoaderFactory();
            this.userCosmeticsLoaderFactory = new UserCosmeticsLoaderFactory();
            this.userStatsLoaderFactory = new UserStatsLoaderFactory();

            this.arenaLoader = (ArenaLoader) arenaLoaderFactory.createLoader();
            this.userCoinsLoader = (UserCoinsLoader) userCoinsLoaderFactory.createLoader();
            this.userCosmeticsLoader = (UserCosmeticsLoader) userCosmeticsLoaderFactory.createLoader();
            this.userStatsLoader = (UserStatsLoader) userStatsLoaderFactory.createLoader();

            this.arenaManager = new ArenaManagerImpl(arenaLoader);
            this.gameManager = new GameManagerImpl();
            this.userCoinsManager = new UserCoinsManagerImpl(userCoinsLoader);
            this.userCosmeticsManager = new UserCosmeticsManagerImpl(userCosmeticsLoader);

            if (configuratorsInitializer.getPluginConfiguration().isUseDefaultStatsSystem())
                this.userStatsManager = new UserStatsManagerImpl(userStatsLoader);
        }

        private void registerModulesBeforeOtherModules(List<Module> modules) {
            modules.forEach(this::registerModules);
        }

        protected Module checkBeforeRegistration(String moduleName) {
            Optional<Module> moduleOptional = moduleManager.getModule(moduleName);

            if (!moduleOptional.isPresent()) {
                throw new ModuleException(moduleName + " module must be registered before other modules");
            }

            return moduleOptional.get();
        }

        public LibraryConfiguration withDefaultConfiguration() {
            this.registerGameListeners();

            return new LibraryConfiguration(plugin, configuratorsInitializer, this);
        }

        public Builder registerModules(Module... modules) {
            for (Module module : modules) {
                moduleManager.registerModule(module);
            }
            return this;
        }

        private void registerGameListeners() {
            Listener[] listeners = {
                    new BlockBreakListener(configuratorsInitializer.getGameConfiguration(), gameManager),
                    new BlockPlaceListener(configuratorsInitializer.getGameConfiguration(), gameManager),
                    new PlayerDropItemListener(configuratorsInitializer.getGameConfiguration(), gameManager),
                    new PlayerJoinListener(userCoinsManager)
            };

            for (Listener listener : listeners) {
                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }

        }

        public Builder setArenaManager(ArenaManager arenaManager) {
            this.arenaManager = arenaManager;
            return this;
        }

        public Builder setUserCosmeticsManager(UserCosmeticsManager userCosmeticsManager) {
            this.userCosmeticsManager = userCosmeticsManager;
            return this;
        }

        public Builder setUserCoinsManager(UserCoinsManager userCoinsManager) {
            this.userCoinsManager = userCoinsManager;
            return this;
        }

        public Builder setUserStatsManager(UserStatsManager userStatsManager) {
            this.userStatsManager = userStatsManager;
            return this;
        }

        public Builder setCosmeticsManager(CosmeticsManager cosmeticsManager) {
            this.cosmeticsManager = cosmeticsManager;
            return this;
        }

        public Builder setGameManager(GameManager gameManager) {
            this.gameManager = gameManager;
            return this;
        }

        public Builder setArenaLoader(ArenaLoader arenaLoader) {
            this.arenaLoader = arenaLoader;
            return this;
        }

        public Builder setUserCosmeticsLoader(UserCosmeticsLoader userCosmeticsLoader) {
            this.userCosmeticsLoader = userCosmeticsLoader;
            return this;
        }

        public Builder setUserCoinsLoader(UserCoinsLoader userCoinsLoader) {
            this.userCoinsLoader = userCoinsLoader;
            return this;
        }

        public Builder setUserStatsLoader(UserStatsLoader userStatsLoader) {
            this.userStatsLoader = userStatsLoader;
            return this;
        }

        public Builder setUserCoinsFactory(UserCoinsFactory userCoinsFactory) {
            this.userCoinsFactory = userCoinsFactory;
            return this;
        }

        public Builder setArenaFactory(ArenaFactory arenaFactory) {
            this.arenaFactory = arenaFactory;
            return this;
        }

        public Builder setUserStatsFactory(UserStatsFactory userStatsFactory) {
            this.userStatsFactory = userStatsFactory;
            return this;
        }

        public Builder setUserCosmeticsFactory(UserCosmeticsFactory userCosmeticsFactory) {
            this.userCosmeticsFactory = userCosmeticsFactory;
            return this;
        }

        public Builder setUserCoinsLoaderFactory(LoaderFactory<UserCoins> userCoinsLoaderFactory) {
            this.userCoinsLoaderFactory = userCoinsLoaderFactory;
            return this;
        }

        public Builder setArenaLoaderFactory(LoaderFactory<Arena> arenaLoaderFactory) {
            this.arenaLoaderFactory = arenaLoaderFactory;
            return this;
        }

        public Builder setUserCosmeticsLoaderFactory(LoaderFactory<UserCosmetics> userCosmeticsLoaderFactory) {
            this.userCosmeticsLoaderFactory = userCosmeticsLoaderFactory;
            return this;
        }

        public Builder setUserStatsLoaderFactory(LoaderFactory<UserStats> userStatsLoaderFactory) {
            this.userStatsLoaderFactory = userStatsLoaderFactory;
            return this;
        }

        public LibraryConfiguration build() {
            registerGameListeners();

            return new LibraryConfiguration(plugin, configuratorsInitializer, this);
        }
    }
}
