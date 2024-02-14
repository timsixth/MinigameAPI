package pl.timsixth.exampleminigame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.exampleminigame.command.AdminExampleMiniGameCommand;
import pl.timsixth.exampleminigame.command.ExampleMiniGameCommand;
import pl.timsixth.exampleminigame.config.ConfigFile;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.configurators.MyCommandConfigurator;
import pl.timsixth.exampleminigame.configurators.MyGameConfigurator;
import pl.timsixth.exampleminigame.configurators.MyPluginConfigurator;
import pl.timsixth.exampleminigame.cosmetics.FireworkCosmetic;
import pl.timsixth.exampleminigame.cosmetics.HeartParticleCosmetic;
import pl.timsixth.exampleminigame.listeners.BlockBreakListener;
import pl.timsixth.exampleminigame.listeners.PlayerInteractListener;
import pl.timsixth.exampleminigame.manager.MyGameManager;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.command.CommandRegistration;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;

public class ExampleMiniGamePlugin extends MiniGame {

    private ExampleMiniGameCommand exampleMiniGameCommand;
    private AdminExampleMiniGameCommand adminExampleMiniGameCommand;
    private Messages messages;
    private Settings settings;

    @Override
    public void onEnable() {
        //register configurators
        setDefaultGameConfigurator(new MyGameConfigurator());
        setDefaultPluginConfigurator(new MyPluginConfigurator());
        setDefaultCommandConfigurator(new MyCommandConfigurator());

        //set loader which loads data from many YAML files
        //setArenaFileLoader(new ArenaManyFilesLoader());

        super.onEnable();

        getConfig().options().copyDefaults(true);
        saveConfig();

        initConfiguration();

        setGameManager(new MyGameManager(this, settings, messages));

        //register game listeners, block breaking, block placing, items dropping
        super.registerGameListeners();

        registerCommands();
        registerListeners();

        CosmeticsManager cosmeticsManager = getCosmeticsManager();
        cosmeticsManager.addCosmetics(
                new HeartParticleCosmetic(),
                new FireworkCosmetic()
        );

        //loads all data from files or database
        getLoaders().loadAll();
    }

    private void initConfiguration() {
        ConfigFile configFile = new ConfigFile(this);
        settings = new Settings(this);
        messages = new Messages(configFile);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(settings, getGameManager(), messages, getUserStatsManager(), getUserCoinsManager()), this);
        pluginManager.registerEvents(new PlayerInteractListener(settings, messages, getGameManager()), this);
    }

    private void registerCommands() {
        exampleMiniGameCommand = new ExampleMiniGameCommand(getCommandConfiguration(), messages, getArenaManager(), getGameManager(), getUserCoinsManager(), getUserStatsManager());
        adminExampleMiniGameCommand = new AdminExampleMiniGameCommand(getCommandConfiguration(), messages, settings, getArenaManager(), getUserCoinsManager());

        CommandRegistration commandRegistration = new CommandRegistration(this);

        commandRegistration.registerCommandWithTabCompleter(exampleMiniGameCommand);
        commandRegistration.registerCommandWithTabCompleter(adminExampleMiniGameCommand);
    }

    @Override
    public ParentCommand getPlayerCommand() {
        return exampleMiniGameCommand;
    }

    @Override
    public ParentCommand getAdminCommand() {
        return adminExampleMiniGameCommand;
    }
}
