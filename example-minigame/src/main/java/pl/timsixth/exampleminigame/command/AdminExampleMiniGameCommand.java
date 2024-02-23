package pl.timsixth.exampleminigame.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.command.subcommand.admin.*;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.tabcompleter.AdminExampleMiniGameCommandTabCompleter;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.tabcompleter.BaseTabCompleter;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.util.ChatUtil;

public class AdminExampleMiniGameCommand extends ParentCommand {

    private final Messages messages;
    private final ArenaManager arenaManager;
    private final UserCoinsManager userCoinsManager;

    public AdminExampleMiniGameCommand(CommandConfiguration commandConfiguration, Messages messages,
                                       Settings settings,
                                       ArenaManager arenaManager, UserCoinsManager userCoinsManager) {
        super("minigame.admin", true, true, true, commandConfiguration);
        this.messages = messages;
        this.arenaManager = arenaManager;
        this.userCoinsManager = userCoinsManager;
        addSubCommand(new SetLobbySubCommand(settings, messages));
        addSubCommand(new CreateSubCommand(arenaManager, messages));
        addSubCommand(new SetSpawnSubCommand(arenaManager, messages));
        addSubCommand(new SetGameLobbySubCommand(arenaManager, messages));
        addSubCommand(new AddCoinsSubCommand(userCoinsManager, messages));
        addSubCommand(new RemoveCoinsSubCommand(userCoinsManager, messages));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        for (String message : messages.getAdminHelp()) {
            player.sendMessage(ChatUtil.chatColor(message));
        }

        return false;
    }

    @Override
    public String getName() {
        return "aem";
    }

    @Override
    public BaseTabCompleter getTabCompleter() {
        return new AdminExampleMiniGameCommandTabCompleter(this, arenaManager, userCoinsManager);
    }
}
