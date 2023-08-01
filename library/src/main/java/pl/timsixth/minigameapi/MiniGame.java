package pl.timsixth.minigameapi;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.migration.Migrations;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.loader.ArenaFileLoader;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.arena.manager.ArenaManagerImpl;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.loader.UserCoinsLoader;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManagerImpl;
import pl.timsixth.minigameapi.coins.migrations.CreateUserCoinsTableMigration;
import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.configurators.CommandConfigurator;
import pl.timsixth.minigameapi.configuration.configurators.GameConfigurator;
import pl.timsixth.minigameapi.configuration.configurators.PluginConfigurator;
import pl.timsixth.minigameapi.configuration.type.DefaultCommandConfiguration;
import pl.timsixth.minigameapi.configuration.type.DefaultGameConfiguration;
import pl.timsixth.minigameapi.configuration.type.DefaultPluginConfiguration;
import pl.timsixth.minigameapi.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.cosmetics.impl.CosmeticsManagerImpl;
import pl.timsixth.minigameapi.cosmetics.user.UserCosmeticsDbModel;
import pl.timsixth.minigameapi.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.cosmetics.user.manager.UserCosmeticsManagerImpl;
import pl.timsixth.minigameapi.cosmetics.user.migrations.CreateUserCosmeticsTableMigration;
import pl.timsixth.minigameapi.game.GameManager;
import pl.timsixth.minigameapi.game.impl.GameManagerImpl;
import pl.timsixth.minigameapi.listener.BlockBreakListener;
import pl.timsixth.minigameapi.listener.BlockPlaceListener;
import pl.timsixth.minigameapi.listener.PlayerDropItemListener;
import pl.timsixth.minigameapi.loader.Loaders;
import pl.timsixth.minigameapi.stats.loader.DefaultUserStatsLoader;
import pl.timsixth.minigameapi.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.stats.manager.UserStatsManagerImpl;
import pl.timsixth.minigameapi.stats.migrations.CreateUserStatsTable;
import pl.timsixth.minigameapi.stats.model.UserStatsDbModel;

/**
 * Represents every minigame
 */
@Getter
@Setter
public class MiniGame extends JavaPlugin {

    private Configurator<DefaultGameConfiguration> defaultGameConfigurator;
    private Configurator<DefaultPluginConfiguration> defaultPluginConfigurator;
    private Configurator<DefaultCommandConfiguration> defaultCommandConfigurator;

    private ArenaManager<ArenaFileModel> arenaManager;
    private UserCoinsManager<UserCoinsDbModel> userCoinsManager;
    private UserCosmeticsManager<UserCosmeticsDbModel> userCosmeticsManager;
    private UserStatsManager<UserStatsDbModel> userStatsManager;
    private CosmeticsManager cosmeticsManager;
    private GameManager gameManager;

    private ArenaFileLoader arenaFileLoader;
    private UserCoinsLoader userCoinsLoader;
    private UserCosmeticsLoader userCosmeticsLoader;
    private DefaultUserStatsLoader userStatsLoader;

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
        if (defaultGameConfigurator == null) defaultGameConfigurator = new GameConfigurator();
        if (defaultPluginConfigurator == null) defaultPluginConfigurator = new PluginConfigurator();
        if (defaultCommandConfigurator == null) defaultCommandConfigurator = new CommandConfigurator();
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
            userStatsManager = new UserStatsManagerImpl<>(userStatsLoader);
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

        if (getDefaultPluginConfiguration().isUseDefaultStatsSystem()) userStatsLoader = new DefaultUserStatsLoader();
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
     * @return default game configuration {@link DefaultGameConfiguration}
     */
    public DefaultGameConfiguration getDefaultGameConfiguration() {
        return defaultGameConfigurator.configure();
    }

    /**
     * @return default plugin configuration {@link DefaultPluginConfiguration}
     */
    public DefaultPluginConfiguration getDefaultPluginConfiguration() {
        return defaultPluginConfigurator.configure();
    }

    public DefaultCommandConfiguration getDefaultCommandConfiguration() {
        return defaultCommandConfigurator.configure();
    }
}
