package pl.timsixth.thetag;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import pl.timsixth.gui.libray.manager.registration.ActionRegistration;
import pl.timsixth.gui.libray.manager.registration.ActionRegistrationImpl;
import pl.timsixth.gui.libray.model.action.custom.impl.NoneClickAction;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.cosmetics.CosmeticsManager;
import pl.timsixth.thetag.bstats.Metrics;
import pl.timsixth.thetag.command.AdminTheTagCommand;
import pl.timsixth.thetag.command.TheTagCommand;
import pl.timsixth.thetag.config.ConfigFile;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.configurators.MyCommandConfigurator;
import pl.timsixth.thetag.configurators.MyGameConfigurator;
import pl.timsixth.thetag.configurators.MyPluginConfigurator;
import pl.timsixth.thetag.cosmetics.defeat.DefeatLightningCosmetic;
import pl.timsixth.thetag.cosmetics.hit.HitHeartCosmetic;
import pl.timsixth.thetag.cosmetics.walk.*;
import pl.timsixth.thetag.cosmetics.win.WinFireworkCosmetic;
import pl.timsixth.thetag.expansion.TheTagExpansion;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.gui.BuyOrActiveCosmeticAction;
import pl.timsixth.thetag.gui.OpenMenuAction;
import pl.timsixth.thetag.gui.RestAllCosmeticsAction;
import pl.timsixth.thetag.gui.RestAllCosmeticsCategoryAction;
import pl.timsixth.thetag.listener.*;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.manager.MyGameManager;
import pl.timsixth.thetag.manager.ScoreboardManager;
import pl.timsixth.thetag.tabcompleter.AdminTheTagCommandTabCompleter;
import pl.timsixth.thetag.tabcompleter.TheTagCommandTabCompleter;
import pl.timsixth.thetag.version.VersionChecker;

public class TheTagPlugin extends MiniGame {

    @Getter
    private Messages messages;
    @Getter
    private Settings settings;
    private ConfigFile configFile;
    private GameLogic gameLogic;
    @Getter
    private MenuManager menuManager;
    private ActionRegistration actionRegistration;

    @Override
    public void onEnable() {
        setDefaultGameConfigurator(new MyGameConfigurator());
        setDefaultPluginConfigurator(new MyPluginConfigurator());

        super.onEnable();

        getConfig().options().copyDefaults(true);
        saveConfig();

        initConfiguration();

        setDefaultCommandConfigurator(new MyCommandConfigurator(messages));

        ScoreboardManager scoreboardManager = new ScoreboardManager(settings);

        setGameManager(new MyGameManager(settings, this, messages, scoreboardManager, getUserStatsManager(), getUserCosmeticsManager()));

        gameLogic = new GameLogic(getGameManager(), getUserStatsManager(), messages, getArenaManager(), settings, getUserCosmeticsManager());

        actionRegistration = new ActionRegistrationImpl();
        actionRegistration.register(
                new NoneClickAction(),
                new BuyOrActiveCosmeticAction(),
                new OpenMenuAction(),
                new RestAllCosmeticsAction(),
                new RestAllCosmeticsCategoryAction()
        );

        menuManager = new MenuManager(actionRegistration, configFile);

        super.registerGameListeners();

        registerCommands();
        registerTabCompleters();
        registerListeners();
        registerActions();

        new VersionChecker(this).checkVersion();

        CosmeticsManager cosmeticsManager = getCosmeticsManager();
        cosmeticsManager.addCosmetic(new DefeatLightningCosmetic());
        cosmeticsManager.addCosmetic(new WinFireworkCosmetic());

        cosmeticsManager.addCosmetic(new WalkHeartCosmetic());
        cosmeticsManager.addCosmetic(new WalkSlimeCosmetic());
        cosmeticsManager.addCosmetic(new WalkNoteCosmetic());
        cosmeticsManager.addCosmetic(new WalkDripLavaCosmetic());
        cosmeticsManager.addCosmetic(new WalkDripWaterCosmetic());

        cosmeticsManager.addCosmetic(new HitHeartCosmetic());

        new Metrics(this, 18831);

        if (!initPlaceHolderApi()) {
            Bukkit.getLogger().warning("[TheTag] Please download PlaceholderAPI, if you want to use placeholders.");
        }

        getLoaders().loadAll();
        menuManager.load();
    }

    private void initConfiguration() {
        configFile = new ConfigFile(this);
        settings = new Settings(this);
        messages = new Messages(configFile);
    }

    private void registerListeners() {
        Listener[] listeners = {
                new EntityDamageByEntityListener(getGameManager(), settings, gameLogic),
                new InventoryClickListener(getGameManager()),
                new PlayerQuitListener(gameLogic),
                new PlayerKickListener(gameLogic),
                new FoodLevelChangeListener(getGameManager()),
                new pl.timsixth.gui.libray.listener.InventoryClickListener(menuManager),
                new PlayerJoinListener(getUserCoinsManager()),
                new PlayerMoveListener(getGameManager(), gameLogic),
                new PlayerInteractListener(settings, messages, getGameManager())
        };

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        getCommand("thetag").setExecutor(new TheTagCommand(getDefaultCommandConfiguration(), messages, getArenaManager(), getGameManager(), getUserCoinsManager(), getUserStatsManager(), menuManager, gameLogic));
        getCommand("thetagadmin").setExecutor(new AdminTheTagCommand(getDefaultCommandConfiguration(), messages, settings, menuManager, configFile, getArenaManager(), getUserCoinsManager()));
    }

    private void registerTabCompleters() {
        getCommand("thetag").setTabCompleter(new TheTagCommandTabCompleter(getArenaManager()));
        getCommand("thetagadmin").setTabCompleter(new AdminTheTagCommandTabCompleter(getArenaManager(), getUserCoinsManager()));
    }

    private boolean initPlaceHolderApi() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return false;
        }
        new TheTagExpansion(getUserStatsManager(), getUserCoinsManager()).register();

        return true;
    }

    private void registerActions() {
        actionRegistration.register(new BuyOrActiveCosmeticAction(), new NoneClickAction(), new RestAllCosmeticsCategoryAction(), new RestAllCosmeticsAction());
    }
}
