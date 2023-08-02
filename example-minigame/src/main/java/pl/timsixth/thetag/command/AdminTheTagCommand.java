package pl.timsixth.thetag.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.command.ParentCommand;
import pl.timsixth.minigameapi.configuration.type.DefaultCommandConfiguration;
import pl.timsixth.minigameapi.util.ChatUtil;
import pl.timsixth.thetag.command.subcommand.thetagadmin.*;
import pl.timsixth.thetag.config.ConfigFile;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.PlayerUtil;

public class AdminTheTagCommand extends ParentCommand {

    private final Messages messages;

    public AdminTheTagCommand(DefaultCommandConfiguration defaultCommandConfiguration, Messages messages,
                              Settings settings, MenuManager menuManager, ConfigFile configFile,
                              ArenaManager<ArenaFileModel> arenaManager, UserCoinsManager<UserCoinsDbModel> userCoinsManager) {
        super("thetag.admin", true, true, true, defaultCommandConfiguration);
        this.messages = messages;
        getSubCommands().add(new SetLobbySubCommand(settings, messages));
        getSubCommands().add(new ReloadSubCommand(configFile, menuManager, messages));
        getSubCommands().add(new CreateSubCommand(arenaManager, messages));
        getSubCommands().add(new SetSpawnSubCommand(arenaManager, messages));
        getSubCommands().add(new SetGameLobbySubCommand(arenaManager, messages));
        getSubCommands().add(new AddCoinsSubCommand(userCoinsManager, messages));
        getSubCommands().add(new RemoveCoinsSubCommand(userCoinsManager, messages));
    }

    @Override
    protected boolean executeCommand(CommandSender sender) {
        Player player = (Player) sender;

        for (String message : messages.getAdminHelp()) {
            PlayerUtil.sendMessage(player, ChatUtil.chatColor(message));
        }

        return false;
    }
}
