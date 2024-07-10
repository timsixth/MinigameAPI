package pl.timsixth.minigameapi.api;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.migration.Migrations;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.ArenaImpl;
import pl.timsixth.minigameapi.api.arena.MultiFilesArena;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactory;
import pl.timsixth.minigameapi.api.arena.loader.ArenaLoader;
import pl.timsixth.minigameapi.api.arena.loader.ArenaSingleFileLoader;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.SingleFileUserCoinsAdapter;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.factory.UserCoinsFactory;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.coins.migrations.CreateUserCoinsTableMigration;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.SingleFileUserCosmeticsAdapter;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.factory.UserCosmeticsFactory;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.migrations.CreateUserCosmeticsTableMigration;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.module.ModuleManager;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactory;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.migrations.CreateUserStatsTable;
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

    private static ArenaFactory arenaFactory;
    private static UserCoinsFactory userCoinsFactory;
    private static UserStatsFactory userStatsFactory;
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
        initMigrations();
        initLoaders();
        loadData();

        getModuleManager().enableModules();
    }

    @Override
    public void onDisable() {
        loaders.unregisterLoaders();

        getModuleManager().disableModules();
        getModuleManager().unregisterAllModules();
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

    private void initMigrations() {
        if (!getPluginConfiguration().isUseDataBase()) return;

        Migrations migrations = DatabasesApiPlugin.getApi().getMigrations();
        CreateUserCoinsTableMigration createUserCoinsTableMigration = new CreateUserCoinsTableMigration();
        CreateUserCosmeticsTableMigration createUserCosmeticsTableMigration = new CreateUserCosmeticsTableMigration();

        if (getPluginConfiguration().isUseDefaultStatsSystem()) {
            CreateUserStatsTable createUserStatsTable = new CreateUserStatsTable();
            migrations.addMigration(createUserStatsTable);

            migrations.migrate(createUserStatsTable);
        }

        migrations.addMigration(createUserCoinsTableMigration);
        migrations.addMigration(createUserCosmeticsTableMigration);

        migrations.migrate(createUserCoinsTableMigration);
        migrations.migrate(createUserCosmeticsTableMigration);
    }

    @Deprecated
    protected void registerGameListeners() {
    }

    /**
     * Registers serializable classes. The method uses Bukkit serialization system to serialize and deserialize objects
     */
    protected void registerSerializableClasses() {
        ConfigurationSerialization.registerClass(ArenaImpl.class);
        ConfigurationSerialization.registerClass(MultiFilesArena.class);
        ConfigurationSerialization.registerClass(SingleFileUserCoinsAdapter.class, "SingleFileUserCoinsAdapter");
        ConfigurationSerialization.registerClass(SingleFileUserCosmeticsAdapter.class, "SingleFileUserCosmeticsAdapter");
        ConfigurationSerialization.registerClass(OptionsImpl.class, "options");
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

    public static ArenaFactory getArenaFactory() {
        return arenaFactory;
    }

    @Deprecated
    protected static void setArenaFactory(ArenaFactory arenaFactory) {
        MiniGame.arenaFactory = arenaFactory;
    }

    public static UserCoinsFactory getUserCoinsFactory() {
        return userCoinsFactory;
    }

    @Deprecated
    protected static void setUserCoinsFactory(UserCoinsFactory userCoinsFactory) {
        MiniGame.userCoinsFactory = userCoinsFactory;
    }

    public static UserStatsFactory getUserStatsFactory() {
        return userStatsFactory;
    }

    @Deprecated
    public static void setUserStatsFactory(UserStatsFactory userStatsFactory) {
        MiniGame.userStatsFactory = userStatsFactory;
    }

    public static UserCosmeticsFactory getUserCosmeticsFactory() {
        return userCosmeticsFactory;
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
        //this.defaultGameConfigurator = defaultGameConfigurator;
    }

    public Configurator<PluginConfiguration> getDefaultPluginConfigurator() {
        return configuratorsInitializer.getPluginConfigurator();
    }

    @Deprecated
    public void setDefaultPluginConfigurator(Configurator<PluginConfiguration> defaultPluginConfigurator) {
        //  this.defaultPluginConfigurator = defaultPluginConfigurator;
    }

    @Deprecated
    public Configurator<CommandConfiguration> getDefaultCommandConfigurator() {
        return configuratorsInitializer.getCommandConfigurator();
    }

    @Deprecated
    public void setDefaultCommandConfigurator(Configurator<CommandConfiguration> defaultCommandConfigurator) {
        // this.defaultCommandConfigurator = defaultCommandConfigurator;
    }

    public ArenaManager getArenaManager() {
        return libraryConfiguration.getArenaManager();
    }

    @Deprecated
    public void setArenaManager(ArenaManager arenaManager) {
        //  this.arenaManager = arenaManager;
    }

    public UserCoinsManager getUserCoinsManager() {
        return libraryConfiguration.getUserCoinsManager();
    }

    @Deprecated
    public void setUserCoinsManager(UserCoinsManager userCoinsManager) {
        // this.userCoinsManager = userCoinsManager;
    }

    public UserCosmeticsManager getUserCosmeticsManager() {
        return libraryConfiguration.getUserCosmeticsManager();
    }

    @Deprecated
    public void setUserCosmeticsManager(UserCosmeticsManager userCosmeticsManager) {
        //  this.userCosmeticsManager = userCosmeticsManager;
    }

    public UserStatsManager getUserStatsManager() {
        return libraryConfiguration.getUserStatsManager();
    }

    @Deprecated
    public void setUserStatsManager(UserStatsManager userStatsManager) {
        // this.userStatsManager = userStatsManager;
    }

    public GameManager getGameManager() {
        return libraryConfiguration.getGameManager();
    }

    @Deprecated
    public void setGameManager(GameManager gameManager) {
        //  this.gameManager = gameManager;
    }

    public CosmeticsManager getCosmeticsManager() {
        return libraryConfiguration.getCosmeticsManager();
    }

    @Deprecated
    public void setCosmeticsManager(CosmeticsManager cosmeticsManager) {
        // this.cosmeticsManager = cosmeticsManager;
    }

    public ArenaLoader getArenaLoader() {
        return libraryConfiguration.getArenaLoader();
    }

    @Deprecated
    public void setArenaLoader(ArenaLoader arenaLoader) {
        //  this.arenaLoader = arenaLoader;
    }

    public UserCoinsLoader getUserCoinsLoader() {
        return libraryConfiguration.getUserCoinsLoader();
    }

    @Deprecated
    public void setUserCoinsLoader(UserCoinsLoader userCoinsLoader) {
        //  this.userCoinsLoader = userCoinsLoader;
    }

    public UserCosmeticsLoader getUserCosmeticsLoader() {
        return libraryConfiguration.getUserCosmeticsLoader();
    }

    @Deprecated
    public void setUserCosmeticsLoader(UserCosmeticsLoader userCosmeticsLoader) {
        //  this.userCosmeticsLoader = userCosmeticsLoader;
    }

    public UserStatsLoader getUserStatsLoader() {
        return libraryConfiguration.getUserStatsLoader();
    }

    @Deprecated
    public void setUserStatsLoader(UserStatsLoader userStatsLoader) {
        // this.userStatsLoader = userStatsLoader;
    }

    public LoaderFactory<UserCoins> getUserCoinsLoaderFactory() {
        return libraryConfiguration.getUserCoinsLoaderFactory();
    }

    @Deprecated
    public void setUserCoinsLoaderFactory(LoaderFactory<UserCoins> userCoinsLoaderFactory) {
        //  this.userCoinsLoaderFactory = userCoinsLoaderFactory;
    }

    public LoaderFactory<Arena> getArenaLoaderFactory() {
        return libraryConfiguration.getArenaLoaderFactory();
    }

    @Deprecated
    public void setArenaLoaderFactory(LoaderFactory<Arena> arenaLoaderFactory) {
        //  this.arenaLoaderFactory = arenaLoaderFactory;
    }

    public LoaderFactory<UserCosmetics> getUserCosmeticsLoaderFactory() {
        return libraryConfiguration.getUserCosmeticsLoaderFactory();
    }

    @Deprecated
    public void setUserCosmeticsLoaderFactory(LoaderFactory<UserCosmetics> userCosmeticsLoaderFactory) {
        // this.userCosmeticsLoaderFactory = userCosmeticsLoaderFactory;
    }

    public LoaderFactory<UserStats> getUserStatsLoaderFactory() {
        return libraryConfiguration.getUserStatsLoaderFactory();
    }

    @Deprecated
    public void setUserStatsLoaderFactory(LoaderFactory<UserStats> userStatsLoaderFactory) {
        // this.userStatsLoaderFactory = userStatsLoaderFactory;
    }
}
