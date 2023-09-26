package pl.timsixth.thetag.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.tabcompleter.BaseTabCompleter;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.thetag.command.subcommand.thetagadmin.*;
import pl.timsixth.thetag.config.ConfigFile;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.tabcompleter.AdminTheTagCommandTabCompleter;
import pl.timsixth.thetag.util.PlayerUtil;

public class AdminTheTagCommand extends ParentCommand {

    private final Messages messages;
    private final ArenaManager arenaManager;
    private final UserCoinsManager userCoinsManager;

    public AdminTheTagCommand(CommandConfiguration commandConfiguration, Messages messages,
                              Settings settings, MenuManager menuManager, ConfigFile configFile,
                              ArenaManager arenaManager, UserCoinsManager userCoinsManager) {
        super("thetag.admin", true, true, true, commandConfiguration);
        this.messages = messages;
        this.arenaManager = arenaManager;
        this.userCoinsManager = userCoinsManager;
        getSubCommands().add(new SetLobbySubCommand(settings, messages));
        getSubCommands().add(new ReloadSubCommand(configFile, menuManager, messages));
        getSubCommands().add(new CreateSubCommand(arenaManager, messages));
        getSubCommands().add(new SetSpawnSubCommand(arenaManager, messages));
        getSubCommands().add(new SetGameLobbySubCommand(arenaManager, messages));
        getSubCommands().add(new AddCoinsSubCommand(userCoinsManager, messages));
        getSubCommands().add(new RemoveCoinsSubCommand(userCoinsManager, messages));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        for (String message : messages.getAdminHelp()) {
            PlayerUtil.sendMessage(player, ChatUtil.chatColor(message));
        }

        return false;
    }

    @Override
    public String getName() {
        return "tta";
    }

    @Override
    public BaseTabCompleter getTabCompleter() {
        return new AdminTheTagCommandTabCompleter(this, arenaManager, userCoinsManager);
    }
}
