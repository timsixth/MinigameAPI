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
import pl.timsixth.minigameapi.api.coins.SingleFileUserCoinsAdapter;
import pl.timsixth.minigameapi.api.coins.SingleYamlUserCoins;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactory;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.SingleFileUserCosmeticsAdapter;
import pl.timsixth.minigameapi.api.cosmetics.user.SingleYamlUserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactory;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.logging.MiniGameLogger;
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
    }

    @Override
    public void onEnable() {
        instance = this;
        initConfiguration();
        libraryConfiguration = configure();

        setArenaFactory(libraryConfiguration.getArenaFactory());
        setUserCoinsFactory(libraryConfiguration.getUserCoinsFactory());
        setUserStatsFactory(libraryConfiguration.getUserStatsFactory());
        setUserCosmeticsFactory(libraryConfiguration.getUserCosmeticsFactory());

        registerSerializableClasses();
        initLoaders();

        getModuleManager().enableModules();

        loadData();
    }

    @Override
    public void onDisable() {
        loaders.unregisterLoaders();

        getModuleManager().disableModules();
    }

    protected ConfiguratorsInitializer loadConfigurators() {
        return ConfiguratorsInitializer.builder().build();
    }

    protected LibraryConfiguration configure() {
        return new LibraryConfiguration(this, configuratorsInitializer)
                .builder()
                .withDefaultConfiguration();
    }

    protected abstract void initConfiguration();

    private void initLoaders() {
        loaders = new Loaders(getPluginConfiguration());
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

    @Deprecated
    protected void registerGameListeners() {
    }

    /**
     * Registers serializable classes. The method uses Bukkit serialization system to serialize and deserialize objects
     */
    protected void registerSerializableClasses() {
        ConfigurationSerialization.registerClass(ArenaImpl.class, "ArenaImpl");
        ConfigurationSerialization.registerClass(MultiFilesArena.class, "MultiFilesArena");
        ConfigurationSerialization.registerClass(SingleFileUserCoinsAdapter.class, "SingleFileUserCoinsAdapter");
        ConfigurationSerialization.registerClass(SingleFileUserCosmeticsAdapter.class, "SingleFileUserCosmeticsAdapter");
        ConfigurationSerialization.registerClass(OptionsImpl.class, "options");
        ConfigurationSerialization.registerClass(SingleYamlUserCoins.class, "SingleYamlUserCoins");
        ConfigurationSerialization.registerClass(SingleYamlUserCosmetics.class, "SingleYamlUserCosmetics");
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

    /**
     * @return command configuration {@link CommandConfiguration}
     */
    @Deprecated
    public CommandConfiguration getCommandConfiguration() {
        return configuratorsInitializer.getCommandConfiguration();
    }

    /**
     * Gets player command
     *
     * @return player command
     */
    @Deprecated
    public ParentCommand getPlayerCommand() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets admin command
     *
     * @return admin command
     */
    @Deprecated
    public ParentCommand getAdminCommand() {
        throw new UnsupportedOperationException();
    }

    public ModuleManager getModuleManager() {
        return libraryConfiguration.getModuleManager();
    }

    @Deprecated
    protected static void setArenaFactory(ArenaFactory arenaFactory) {
        MiniGame.arenaFactory = arenaFactory;
    }

    @Deprecated
    protected static void setUserCoinsFactory(UserCoinsFactory userCoinsFactory) {
        MiniGame.userCoinsFactory = userCoinsFactory;
    }

    @Deprecated
    protected static void setUserStatsFactory(UserStatsFactory userStatsFactory) {
        MiniGame.userStatsFactory = userStatsFactory;
    }

    @Deprecated
    protected static void setUserCosmeticsFactory(UserCosmeticsFactory userCosmeticsFactory) {
        MiniGame.userCosmeticsFactory = userCosmeticsFactory;
    }

    public Configurator<GameConfiguration> getDefaultGameConfigurator() {
        return configuratorsInitializer.getGameConfigurator();
    }

    @Deprecated
    public void setDefaultGameConfigurator(Configurator<GameConfiguration> defaultGameConfigurator) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public Configurator<PluginConfiguration> getDefaultPluginConfigurator() {
        return configuratorsInitializer.getPluginConfigurator();
    }

    @Deprecated
    public void setDefaultPluginConfigurator(Configurator<PluginConfiguration> defaultPluginConfigurator) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    @Deprecated
    public Configurator<CommandConfiguration> getDefaultCommandConfigurator() {
        return configuratorsInitializer.getCommandConfigurator();
    }

    @Deprecated
    public void setDefaultCommandConfigurator(Configurator<CommandConfiguration> defaultCommandConfigurator) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public ArenaManager getArenaManager() {
        return libraryConfiguration.getArenaManager();
    }

    @Deprecated
    public void setArenaManager(ArenaManager arenaManager) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public UserCoinsManager getUserCoinsManager() {
        return libraryConfiguration.getUserCoinsManager();
    }

    @Deprecated
    public void setUserCoinsManager(UserCoinsManager userCoinsManager) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public UserCosmeticsManager getUserCosmeticsManager() {
        return libraryConfiguration.getUserCosmeticsManager();
    }

    @Deprecated
    public void setUserCosmeticsManager(UserCosmeticsManager userCosmeticsManager) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public UserStatsManager getUserStatsManager() {
        return libraryConfiguration.getUserStatsManager();
    }

    @Deprecated
    public void setUserStatsManager(UserStatsManager userStatsManager) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public GameManager getGameManager() {
        return libraryConfiguration.getGameManager();
    }

    @Deprecated
    public void setGameManager(GameManager gameManager) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public CosmeticsManager getCosmeticsManager() {
        return libraryConfiguration.getCosmeticsManager();
    }

    @Deprecated
    public void setCosmeticsManager(CosmeticsManager cosmeticsManager) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public ArenaLoader getArenaLoader() {
        return libraryConfiguration.getArenaLoader();
    }

    @Deprecated
    public void setArenaLoader(ArenaLoader arenaLoader) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public UserCoinsLoader getUserCoinsLoader() {
        return libraryConfiguration.getUserCoinsLoader();
    }

    @Deprecated
    public void setUserCoinsLoader(UserCoinsLoader userCoinsLoader) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public UserCosmeticsLoader getUserCosmeticsLoader() {
        return libraryConfiguration.getUserCosmeticsLoader();
    }

    @Deprecated
    public void setUserCosmeticsLoader(UserCosmeticsLoader userCosmeticsLoader) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public UserStatsLoader getUserStatsLoader() {
        return libraryConfiguration.getUserStatsLoader();
    }

    @Deprecated
    public void setUserStatsLoader(UserStatsLoader userStatsLoader) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public LoaderFactory<UserCoins> getUserCoinsLoaderFactory() {
        return libraryConfiguration.getUserCoinsLoaderFactory();
    }

    @Deprecated
    public void setUserCoinsLoaderFactory(LoaderFactory<UserCoins> userCoinsLoaderFactory) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public LoaderFactory<Arena> getArenaLoaderFactory() {
        return libraryConfiguration.getArenaLoaderFactory();
    }

    @Deprecated
    public void setArenaLoaderFactory(LoaderFactory<Arena> arenaLoaderFactory) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public LoaderFactory<UserCosmetics> getUserCosmeticsLoaderFactory() {
        return libraryConfiguration.getUserCosmeticsLoaderFactory();
    }

    @Deprecated
    public void setUserCosmeticsLoaderFactory(LoaderFactory<UserCosmetics> userCosmeticsLoaderFactory) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }

    public LoaderFactory<UserStats> getUserStatsLoaderFactory() {
        return libraryConfiguration.getUserStatsLoaderFactory();
    }

    @Deprecated
    public void setUserStatsLoaderFactory(LoaderFactory<UserStats> userStatsLoaderFactory) {
        throw new UnsupportedOperationException("overwrite configure() method to set it");
    }
}
