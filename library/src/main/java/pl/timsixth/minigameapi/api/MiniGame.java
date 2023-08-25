package pl.timsixth.minigameapi.api;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.migration.Migrations;
import pl.timsixth.minigameapi.api.arena.ArenaFileModel;
import pl.timsixth.minigameapi.api.arena.loader.ArenaFileLoader;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManagerImpl;
import pl.timsixth.minigameapi.api.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.api.coins.loader.UserCoinsLoader;
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
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmeticsDbModel;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManagerImpl;
import pl.timsixth.minigameapi.api.cosmetics.user.migrations.CreateUserCosmeticsTableMigration;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.impl.GameManagerImpl;
import pl.timsixth.minigameapi.api.listener.BlockBreakListener;
import pl.timsixth.minigameapi.api.listener.BlockPlaceListener;
import pl.timsixth.minigameapi.api.listener.PlayerDropItemListener;
import pl.timsixth.minigameapi.api.loader.Loaders;
import pl.timsixth.minigameapi.api.stats.loader.UserStatsLoader;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManagerImpl;
import pl.timsixth.minigameapi.api.stats.migrations.CreateUserStatsTable;
import pl.timsixth.minigameapi.api.stats.model.UserStatsDbModel;

/**
 * Represents every minigame
 */
@Getter
@Setter
public abstract class MiniGame extends JavaPlugin {

    private Configurator<GameConfiguration> defaultGameConfigurator;
    private Configurator<PluginConfiguration> defaultPluginConfigurator;
    private Configurator<CommandConfiguration> defaultCommandConfigurator;

    private ArenaManager<ArenaFileModel> arenaManager;
    private UserCoinsManager<UserCoinsDbModel> userCoinsManager;
    private UserCosmeticsManager<UserCosmeticsDbModel> userCosmeticsManager;
    private UserStatsManager<UserStatsDbModel> userStatsManager;
    private CosmeticsManager cosmeticsManager;
    private GameManager gameManager;

    private ArenaFileLoader arenaFileLoader;
    private UserCoinsLoader userCoinsLoader;
    private UserCosmeticsLoader userCosmeticsLoader;
    private UserStatsLoader userStatsLoader;

    private Loaders loaders;

    @Getter
    private static MiniGame instance;

    @Override
    public void onEnable() {
        instance = this;
        initConfigurators();
        initMigrations();
        initLoaders();
        loadData();
        initManagers();
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
        arenaManager = new ArenaManagerImpl(arenaFileLoader);
        gameManager = new GameManagerImpl();
        userCoinsManager = new UserCoinsManagerImpl(userCoinsLoader);
        userCosmeticsManager = new UserCosmeticsManagerImpl(userCosmeticsLoader);

        if (getDefaultPluginConfiguration().isUseDefaultStatsSystem())
            userStatsManager = new UserStatsManagerImpl(userStatsLoader);
    }

    /**
     * Initializes loaders
     */
    private void initLoaders() {
        cosmeticsManager = new CosmeticsManagerImpl();

        loaders = new Loaders(getDefaultPluginConfiguration());
        arenaFileLoader = new ArenaFileLoader();
        userCoinsLoader = new UserCoinsLoader();
        userCosmeticsLoader = new UserCosmeticsLoader(cosmeticsManager);

        if (getDefaultPluginConfiguration().isUseDefaultStatsSystem()) userStatsLoader = new UserStatsLoader();
    }

    /**
     * Loads data
     */
    private void loadData() {
        loaders.registerLoaders(arenaFileLoader, userCoinsLoader, userCosmeticsLoader);

        if (getDefaultPluginConfiguration().isUseDefaultStatsSystem()) {
            loaders.registerLoader(userStatsLoader);
            loaders.load(userStatsLoader);
        }

        loaders.load(arenaFileLoader, userCoinsLoader);
    }

    /**
     * Initializes migrations
     */
    private void initMigrations() {
        Migrations migrations = DatabasesApiPlugin.getApi().getMigrations();
        CreateUserCoinsTableMigration createUserCoinsTableMigration = new CreateUserCoinsTableMigration();
        CreateUserCosmeticsTableMigration createUserCosmeticsTableMigration = new CreateUserCosmeticsTableMigration();

        if (getDefaultPluginConfiguration().isUseDefaultStatsSystem()) {
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
                new BlockBreakListener(getDefaultGameConfiguration(), gameManager),
                new BlockPlaceListener(getDefaultGameConfiguration(), gameManager),
                new PlayerDropItemListener(getDefaultGameConfiguration(), gameManager)
        };

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * @return default game configuration {@link GameConfiguration}
     */
    public GameConfiguration getDefaultGameConfiguration() {
        return defaultGameConfigurator.configure();
    }

    /**
     * @return default plugin configuration {@link PluginConfiguration}
     */
    public PluginConfiguration getDefaultPluginConfiguration() {
        return defaultPluginConfigurator.configure();
    }

    /**
     * @return default command configuration {@link CommandConfiguration}
     */
    public CommandConfiguration getDefaultCommandConfiguration() {
        return defaultCommandConfigurator.configure();
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
