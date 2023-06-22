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
import pl.timsixth.minigameapi.configuration.configurators.GameConfigurator;
import pl.timsixth.minigameapi.configuration.configurators.PluginConfigurator;
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

@Getter
@Setter
public class MiniGame extends JavaPlugin {

    private Configurator<DefaultGameConfiguration> defaultGameConfigurator;
    private Configurator<DefaultPluginConfiguration> defaultPluginConfigurator;

    private ArenaManager<ArenaFileModel> arenaManager;
    private UserCoinsManager<UserCoinsDbModel> userCoinsManager;
    private UserCosmeticsManager<UserCosmeticsDbModel> userCosmeticsManager;
    private CosmeticsManager cosmeticsManager;
    private GameManager gameManager;

    private ArenaFileLoader arenaFileLoader;
    private UserCoinsLoader userCoinsLoader;
    private UserCosmeticsLoader userCosmeticsLoader;

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

    private void initConfigurators() {
        if (defaultGameConfigurator == null) defaultGameConfigurator = new GameConfigurator();
        if (defaultPluginConfigurator == null) defaultPluginConfigurator = new PluginConfigurator();
    }

    private void initManagers() {
        arenaManager = new ArenaManagerImpl(arenaFileLoader);
        gameManager = new GameManagerImpl();
        userCoinsManager = new UserCoinsManagerImpl(userCoinsLoader);
        userCosmeticsManager = new UserCosmeticsManagerImpl(userCosmeticsLoader);
    }

    private void initLoaders() {
        cosmeticsManager = new CosmeticsManagerImpl();

        loaders = new Loaders(getDefaultPluginConfiguration());
        arenaFileLoader = new ArenaFileLoader();
        userCoinsLoader = new UserCoinsLoader();
        userCosmeticsLoader = new UserCosmeticsLoader(cosmeticsManager);
    }

    private void loadData() {
        loaders.registerLoaders(arenaFileLoader, userCoinsLoader, userCosmeticsLoader);

        loaders.load(arenaFileLoader, userCoinsLoader);
    }

    private void initMigrations() {
        Migrations migrations = DatabasesApiPlugin.getApi().getMigrations();
        CreateUserCoinsTableMigration createUserCoinsTableMigration = new CreateUserCoinsTableMigration();
        CreateUserCosmeticsTableMigration createUserCosmeticsTableMigration = new CreateUserCosmeticsTableMigration();

        migrations.addMigration(createUserCoinsTableMigration);
        migrations.addMigration(createUserCosmeticsTableMigration);

        migrations.migrate(createUserCoinsTableMigration);
        migrations.migrate(createUserCosmeticsTableMigration);
    }

    @Override
    public void onDisable() {
        loaders.unregisterLoaders();
    }

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

    public DefaultGameConfiguration getDefaultGameConfiguration() {
        return defaultGameConfigurator.configure();
    }

    public DefaultPluginConfiguration getDefaultPluginConfiguration() {
        return defaultPluginConfigurator.configure();
    }
}
