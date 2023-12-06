package pl.timsixth.thetag;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import pl.timsixth.guilibrary.core.GUIApi;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.guilibrary.core.model.action.custom.NoneClickAction;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.command.CommandRegistration;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
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
import pl.timsixth.thetag.cosmetics.hit.HitNoteCosmetic;
import pl.timsixth.thetag.cosmetics.hit.HitSlimeCosmetic;
import pl.timsixth.thetag.cosmetics.hit.HitSnowCosmetic;
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
    private TheTagCommand theTagCommand;
    private AdminTheTagCommand adminTheTagCommand;

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

        GUIApi guiApi = new GUIApi(this);
        actionRegistration = guiApi.getActionRegistration();

        registerActions();

        menuManager = new MenuManager(actionRegistration, configFile);

        guiApi.setMenuManager(menuManager);

        super.registerGameListeners();
        guiApi.registerMenuListener();

        registerCommands();
        registerListeners();

        new VersionChecker(this).checkVersion();

        CosmeticsManager cosmeticsManager = getCosmeticsManager();
        cosmeticsManager.addCosmetics(
                new DefeatLightningCosmetic(),
                new WinFireworkCosmetic(),
                new WalkHeartCosmetic(),
                new WalkSlimeCosmetic(),
                new WalkNoteCosmetic(),
                new WalkDripLavaCosmetic(),
                new WalkDripWaterCosmetic(),
                new WalkSnowCosmetic(),
                new HitHeartCosmetic(),
                new HitSlimeCosmetic(),
                new HitNoteCosmetic(),
                new HitSnowCosmetic()
        );

        new Metrics(this, 18831);

        if (!initPlaceHolderApi()) {
            Bukkit.getLogger().warning("[TheTag] Please download PlaceholderAPI, if you want to use placeholders.");
        }

        getLoaders().loadAll();
        menuManager.load();
    }

    @Override
    public ParentCommand getPlayerCommand() {
        return theTagCommand;
    }

    @Override
    public ParentCommand getAdminCommand() {
        return adminTheTagCommand;
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
                new PlayerJoinListener(getUserCoinsManager()),
                new PlayerMoveListener(getGameManager(), gameLogic),
                new PlayerInteractListener(settings, messages, getGameManager())
        };

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        theTagCommand = new TheTagCommand(getDefaultCommandConfiguration(), messages, getArenaManager(), getGameManager(), getUserCoinsManager(), getUserStatsManager(), menuManager, gameLogic);
        adminTheTagCommand = new AdminTheTagCommand(getDefaultCommandConfiguration(), messages, settings, menuManager, configFile, getArenaManager(), getUserCoinsManager());

        CommandRegistration commandRegistration = new CommandRegistration(this);

        commandRegistration.registerCommandWithTabCompleter(theTagCommand);
        commandRegistration.registerCommandWithTabCompleter(adminTheTagCommand);
    }

    private boolean initPlaceHolderApi() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return false;
        }
        new TheTagExpansion(getUserStatsManager(), getUserCoinsManager()).register();

        return true;
    }

    private void registerActions() {
        actionRegistration.register(
                new NoneClickAction(),
                new BuyOrActiveCosmeticAction(),
                new OpenMenuAction(),
                new RestAllCosmeticsAction(),
                new RestAllCosmeticsCategoryAction()
        );
    }
}
