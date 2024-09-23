package pl.timsixth.exampleminigame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import pl.timsixth.exampleminigame.command.AdminExampleMiniGameCommand;
import pl.timsixth.exampleminigame.command.ExampleMiniGameCommand;
import pl.timsixth.exampleminigame.config.ConfigFile;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.configurators.MyGameConfigurator;
import pl.timsixth.exampleminigame.configurators.MyPluginConfigurator;
import pl.timsixth.exampleminigame.cosmetics.FireworkCosmetic;
import pl.timsixth.exampleminigame.cosmetics.HeartParticleCosmetic;
import pl.timsixth.exampleminigame.listeners.BlockBreakListener;
import pl.timsixth.exampleminigame.listeners.PlayerInteractListener;
import pl.timsixth.exampleminigame.listeners.PlayerKickListener;
import pl.timsixth.exampleminigame.listeners.PlayerQuitListener;
import pl.timsixth.exampleminigame.manager.MyGameManager;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.configuration.ConfiguratorsInitializer;
import pl.timsixth.minigameapi.api.configuration.LibraryConfiguration;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.module.command.CommandRegistration;
import pl.timsixth.minigameapi.api.module.command.CommandsModule;
import pl.timsixth.minigameapi.api.module.mongodb.MongoDbModule;
import pl.timsixth.minigameapi.api.module.mongodb.core.configuration.MongoDbLibraryConfiguration;

public class ExampleMiniGamePlugin extends MiniGame {

    private Messages messages;
    private Settings settings;

    private CommandsModule commandsModule;

    @Override
    protected void onMiniGameEnable() {
        registerCommands();
        registerListeners();

        CosmeticsManager cosmeticsManager = getCosmeticsManager();
        cosmeticsManager.addCosmetics(
                new HeartParticleCosmetic(),
                new FireworkCosmetic()
        );
    }

    @Override
    protected ConfiguratorsInitializer loadConfigurators() {
        return ConfiguratorsInitializer.builder()
                .setGameConfigurator(new MyGameConfigurator())
                .setPluginConfigurator(new MyPluginConfigurator())
                .build();
    }

    //Default library configuration
//    @Override
//    protected LibraryConfiguration configure() {
//        commandsModule = new CommandsModule(this);
//        return new LibraryConfiguration(this, getConfiguratorsInitializer())
//                .builder()
//                .setGameManager(new MyGameManager(this, settings, messages))
//                .registerModules(commandsModule) //new way how to register command module
//                .build();
//    }

//    //When you want to use SQL databases support
//    @Override
//    protected LibraryConfiguration configure() {
//        commandsModule = new CommandsModule(this);
//        return new SQLLibraryConfiguration(this, getConfiguratorsInitializer(),
//                () -> Collections.singletonList(new SQLModule())) //you can register modules before others
//                .builder()
//                .setGameManager(new MyGameManager(this, settings, messages))
//                .registerModules(commandsModule) //new way how to register command module
//                .build();
//    }

    //When you want to use MongoDb support
    @Override
    protected LibraryConfiguration configure() {
        commandsModule = new CommandsModule(this);
        MongoDbModule mongoDbModule = new MongoDbModule();
        return new MongoDbLibraryConfiguration(this, getConfiguratorsInitializer())
                .builder()
                .setGameManager(new MyGameManager(this, settings, messages))
                .registerModules(mongoDbModule, commandsModule) //new way how to register command module
                .build();
    }

    @Override
    protected void initConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        ConfigFile configFile = new ConfigFile(this);
        settings = new Settings(this);
        messages = new Messages(configFile);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(settings, getGameManager(), messages, getUserStatsManager(), getUserCoinsManager()), this);
        pluginManager.registerEvents(new PlayerInteractListener(settings, messages, getGameManager()), this);
        pluginManager.registerEvents(new PlayerQuitListener(getGameManager()), this);
        pluginManager.registerEvents(new PlayerKickListener(getGameManager()), this);
    }

    private void registerCommands() {
        ExampleMiniGameCommand exampleMiniGameCommand = new ExampleMiniGameCommand(messages, getArenaManager(), getGameManager(), getUserCoinsManager(), getUserStatsManager(), getUserCosmeticsManager(), getCosmeticsManager());
        AdminExampleMiniGameCommand adminExampleMiniGameCommand = new AdminExampleMiniGameCommand(messages, settings, getArenaManager(), getUserCoinsManager());

        CommandRegistration commandRegistration = commandsModule.getCommandRegistration();

        commandRegistration.registerCommandWithTabCompleter(exampleMiniGameCommand);
        commandRegistration.registerCommandWithTabCompleter(adminExampleMiniGameCommand);
    }
}
