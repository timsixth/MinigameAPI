package pl.timsixth.minigameapi.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.migration.Migrations;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactory;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactoryImpl;
import pl.timsixth.minigameapi.api.arena.loader.ArenaFileLoader;
import pl.timsixth.minigameapi.api.arena.loader.ArenaLoader;
import pl.timsixth.minigameapi.api.arena.loader.ArenaSingleFileLoader;
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
import pl.timsixth.minigameapi.api.coins.migrations.CreateUserCoinsTableMigration;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultCommandConfigurator;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultGameConfigurator;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultPluginConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.impl.CosmeticsManagerImpl;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.factory.UserCosmeticsLoaderFactory;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManagerImpl;
import pl.timsixth.minigameapi.api.cosmetics.user.migrations.CreateUserCosmeticsTableMigration;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.impl.GameManagerImpl;
import pl.timsixth.minigameapi.api.listener.BlockBreakListener;
import pl.timsixth.minigameapi.api.listener.BlockPlaceListener;
import pl.timsixth.minigameapi.api.listener.PlayerDropItemListener;
import pl.timsixth.minigameapi.api.listener.PlayerJoinListener;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.loader.factory.LoaderFactory;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactory;
import pl.timsixth.minigameapi.api.stats.factory.UserStatsFactoryImpl;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.loader.factory.UserStatsLoaderFactory;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManagerImpl;
import pl.timsixth.minigameapi.api.stats.migrations.CreateUserStatsTable;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.io.File;

/**
 * Represents every MiniGame
 */
@Getter
@Setter
public abstract class MiniGame extends JavaPlugin {

    private Configurator<GameConfiguration> defaultGameConfigurator;
    private Configurator<PluginConfiguration> defaultPluginConfigurator;
    private Configurator<CommandConfiguration> defaultCommandConfigurator;

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

    private Loaders loaders;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static ArenaFactory arenaFactory;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static UserCoinsFactory userCoinsFactory;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static UserStatsFactory userStatsFactory;

    private LoaderFactory<UserCoins> userCoinsLoaderFactory;
    private LoaderFactory<Arena> arenaLoaderFactory;
    private LoaderFactory<UserCosmetics> userCosmeticsLoaderFactory;
    private LoaderFactory<UserStats> userStatsLoaderFactory;

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
    public void onEnable() {
        instance = this;
        initConfigurators();
        initMigrations();
        initModelsFactories();
        initLoadersFactories();
        initLoaders();
        loadData();
        initManagers();
    }

    /**
     * Initializes models factories
     */
    private void initModelsFactories() {
        arenaFactory = new ArenaFactoryImpl();
        userCoinsFactory = new UserCoinsFactoryImpl();
        if (getPluginConfiguration().isUseDefaultStatsSystem()) userStatsFactory = new UserStatsFactoryImpl();
    }

    /**
     * Initializes loaders factories
     */
    private void initLoadersFactories() {
        cosmeticsManager = new CosmeticsManagerImpl();

        userCoinsLoaderFactory = new UserCoinsLoaderFactory();
        arenaLoaderFactory = new ArenaLoaderFactory();
        userCosmeticsLoaderFactory = new UserCosmeticsLoaderFactory(cosmeticsManager);
        userStatsLoaderFactory = new UserStatsLoaderFactory();
    }

    /**
     * Initializes configurators
     */
    private void initConfigurators() {
        if (defaultGameConfigurator == null) defaultGameConfigurator = new DefaultGameConfigurator();
        if (defaultPluginConfigurator == null) defaultPluginConfigurator = new DefaultPluginConfigurator();
        if (defaultCommandConfigurator == null) defaultCommandConfigurator = new DefaultCommandConfigurator();
    }

    /**
     * Initializes managers
     */
    private void initManagers() {
        arenaManager = new ArenaManagerImpl(arenaLoader);
        gameManager = new GameManagerImpl();
        userCoinsManager = new UserCoinsManagerImpl(userCoinsLoader);
        userCosmeticsManager = new UserCosmeticsManagerImpl(userCosmeticsLoader);

        if (getPluginConfiguration().isUseDefaultStatsSystem())
            userStatsManager = new UserStatsManagerImpl(userStatsLoader);
    }

    /**
     * Initializes loaders
     */
    private void initLoaders() {
        loaders = new Loaders(getPluginConfiguration());

        if (arenaLoader == null) arenaLoader = (ArenaLoader) arenaLoaderFactory.createLoader();
        if (userCoinsLoader == null) userCoinsLoader = (UserCoinsLoader) userCoinsLoaderFactory.createLoader();

        if (userCosmeticsLoader == null)
            userCosmeticsLoader = (UserCosmeticsLoader) userCosmeticsLoaderFactory.createLoader();

        if (userStatsLoader == null)
            userStatsLoader = (UserStatsLoader) userStatsLoaderFactory.createLoader();
    }

    /**
     * Loads data
     */
    private void loadData() {
        loaders.registerLoaders(userCoinsLoader, userCosmeticsLoader);

        if (getPluginConfiguration().isUseDefaultStatsSystem()) {
            loaders.registerLoader(userStatsLoader);
            loaders.load(userStatsLoader);
        }

        loaders.registerLoaders(arenaLoader);

        if (arenaLoader instanceof ArenaSingleFileLoader) {
            loaders.load(arenaLoader);
        }

        loaders.load(userCoinsLoader);
    }

    /**
     * Initializes migrations
     */
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

    @Override
    public void onDisable() {
        loaders.unregisterLoaders();
    }

    /**
     * Registers game listeners
     */
    protected void registerGameListeners() {
        Listener[] listeners = {
                new BlockBreakListener(getGameConfiguration(), gameManager),
                new BlockPlaceListener(getGameConfiguration(), gameManager),
                new PlayerDropItemListener(getGameConfiguration(), gameManager),
                new PlayerJoinListener(userCoinsManager)
        };

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * @return game configuration {@link GameConfiguration}
     */
    public GameConfiguration getGameConfiguration() {
        return defaultGameConfigurator.configure();
    }

    /**
     * @return plugin configuration {@link PluginConfiguration}
     */
    public PluginConfiguration getPluginConfiguration() {
        return defaultPluginConfigurator.configure();
    }

    /**
     * @return command configuration {@link CommandConfiguration}
     */
    public CommandConfiguration getCommandConfiguration() {
        return defaultCommandConfigurator.configure();
    }

    /**
     * @param arenaFileLoader arena loader file
     * @deprecated use {@link MiniGame#setArenaLoader(ArenaLoader)}
     */
    @Deprecated
    public void setArenaFileLoader(ArenaFileLoader arenaFileLoader) {
        this.arenaLoader = arenaFileLoader;
    }

    /**
     * @return arena loader file
     * @deprecated use {@link MiniGame#getArenaLoader()}
     */
    @Deprecated
    public ArenaFileLoader getArenaFileLoader() {
        return (ArenaFileLoader) arenaLoader;
    }

    /**
     * Gets player command
     *
     * @return player command
     */
    public abstract ParentCommand getPlayerCommand();

    /**
     * Gets admin command
     *
     * @return admin command
     */
    public abstract ParentCommand getAdminCommand();
}
