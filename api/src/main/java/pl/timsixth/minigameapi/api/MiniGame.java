package pl.timsixth.minigameapi.api;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaImpl;
import pl.timsixth.minigameapi.api.arena.MultiFilesArena;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactory;
import pl.timsixth.minigameapi.api.arena.loader.ArenaLoader;
import pl.timsixth.minigameapi.api.arena.loader.ArenaSingleFileLoader;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.SingleYamlUserCoins;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactory;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.SingleYamlUserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactory;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.module.ModuleManager;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactory;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.util.options.OptionsImpl;

import java.io.File;

/**
 * Represents every MiniGame
 */
public abstract class MiniGame extends JavaPlugin {

    @Getter
    private LibraryConfiguration libraryConfiguration;

    @Getter
    private ConfiguratorsInitializer configuratorsInitializer;

    @Getter
    private Loaders loaders;

    @Getter
    private static ArenaFactory arenaFactory;
    @Getter
    private static UserCoinsFactory userCoinsFactory;
    @Getter
    private static UserStatsFactory userStatsFactory;
    @Getter
    private static UserCosmeticsFactory userCosmeticsFactory;

    /**
     * -- GETTER --
     * Gets instance of MiniGame. Don't use when on a server are more than one MiniGame plugin
     */
    @Getter
    private static MiniGame instance;

    public MiniGame() {
    }

    public MiniGame(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {
        configuratorsInitializer = loadConfigurators();
        onMiniGameLoad();
    }

    @Override
    public void onEnable() {
        instance = this;
        initConfiguration();
        libraryConfiguration = configure();

        initModelFactories();

        registerSerializableClasses();
        initLoaders();

        getModuleManager().enableModules();

        loadData();

        onMiniGameEnable();

        getLoaders().loadAll();
    }

    @Override
    public void onDisable() {
        onMiniGameDisable();
        loaders.unregisterLoaders();

        getModuleManager().disableModules();
    }

    /**
     * Configures configurators of library
     *
     * @return ConfiguratorsInitializer
     */
    protected ConfiguratorsInitializer loadConfigurators() {
        return ConfiguratorsInitializer.builder().build();
    }

    /**
     * Configures loaders, managers, factories
     *
     * @return LibraryConfiguration
     */
    protected LibraryConfiguration configure() {
        return new LibraryConfiguration(this, configuratorsInitializer)
                .builder()
                .withDefaultConfiguration();
    }

    /**
     * This method must have initialization of config.yml and other configuration files
     */
    protected abstract void initConfiguration();

    /**
     * This method calls when minigame plugin is enabling
     */
    protected abstract void onMiniGameEnable();

    /**
     * This method calls when minigame plugin is loading
     */
    protected void onMiniGameLoad() {
    }

    /**
     * This method calls when minigame plugin is disabling
     */
    protected void onMiniGameDisable() {
    }

    private void initLoaders() {
        loaders = new Loaders();
    }

    private void loadData() {
        UserCoinsLoader userCoinsLoader = libraryConfiguration.getUserCoinsLoader();
        loaders.registerLoaders(userCoinsLoader, libraryConfiguration.getUserCosmeticsLoader());

        if (getPluginConfiguration().isUseDefaultStatsSystem()) {
            loaders.registerLoader(libraryConfiguration.getUserStatsLoader());
            loaders.load(libraryConfiguration.getUserStatsLoader());
        }

        ArenaLoader arenaLoader = libraryConfiguration.getArenaLoader();
        loaders.registerLoaders(arenaLoader);

        if (arenaLoader instanceof ArenaSingleFileLoader) {
            loaders.load(arenaLoader);
        }

        loaders.load(userCoinsLoader);
    }

    /**
     * Registers serializable classes. The method uses Bukkit serialization system to serialize and deserialize objects
     */
    protected void registerSerializableClasses() {
        ConfigurationSerialization.registerClass(ArenaImpl.class, "ArenaImpl");
        ConfigurationSerialization.registerClass(MultiFilesArena.class, "MultiFilesArena");
        ConfigurationSerialization.registerClass(OptionsImpl.class, "options");
        ConfigurationSerialization.registerClass(SingleYamlUserCoins.class, "SingleYamlUserCoins");
        ConfigurationSerialization.registerClass(SingleYamlUserCosmetics.class, "SingleYamlUserCosmetics");
    }

    private void initModelFactories() {
        MiniGame.arenaFactory = libraryConfiguration.getArenaFactory();
        MiniGame.userCoinsFactory = libraryConfiguration.getUserCoinsFactory();
        MiniGame.userStatsFactory = libraryConfiguration.getUserStatsFactory();
        MiniGame.userCosmeticsFactory = libraryConfiguration.getUserCosmeticsFactory();
    }

    /**
     * @return game configuration {@link GameConfiguration}
     */
    public GameConfiguration getGameConfiguration() {
        return configuratorsInitializer.getGameConfiguration();
    }

    /**
     * @return plugin configuration {@link PluginConfiguration}
     */
    public PluginConfiguration getPluginConfiguration() {
        return configuratorsInitializer.getPluginConfiguration();
    }

    public ModuleManager getModuleManager() {
        return libraryConfiguration.getModuleManager();
    }

    public Configurator<GameConfiguration> getDefaultGameConfigurator() {
        return configuratorsInitializer.getGameConfigurator();
    }

    public Configurator<PluginConfiguration> getDefaultPluginConfigurator() {
        return configuratorsInitializer.getPluginConfigurator();
    }

    public ArenaManager getArenaManager() {
        return libraryConfiguration.getArenaManager();
    }

    public UserCoinsManager getUserCoinsManager() {
        return libraryConfiguration.getUserCoinsManager();
    }

    public UserCosmeticsManager getUserCosmeticsManager() {
        return libraryConfiguration.getUserCosmeticsManager();
    }

    public UserStatsManager getUserStatsManager() {
        return libraryConfiguration.getUserStatsManager();
    }

    public GameManager getGameManager() {
        return libraryConfiguration.getGameManager();
    }

    public CosmeticsManager getCosmeticsManager() {
        return libraryConfiguration.getCosmeticsManager();
    }

    public ArenaLoader getArenaLoader() {
        return libraryConfiguration.getArenaLoader();
    }

    public UserCoinsLoader getUserCoinsLoader() {
        return libraryConfiguration.getUserCoinsLoader();
    }

    public UserCosmeticsLoader getUserCosmeticsLoader() {
        return libraryConfiguration.getUserCosmeticsLoader();
    }

    public UserStatsLoader getUserStatsLoader() {
        return libraryConfiguration.getUserStatsLoader();
    }

    public LoaderFactory<Arena> getArenaLoaderFactory() {
        return libraryConfiguration.getArenaLoaderFactory();
    }

    public LoaderFactory<UserStats> getUserStatsLoaderFactory() {
        return libraryConfiguration.getUserStatsLoaderFactory();
    }
}
