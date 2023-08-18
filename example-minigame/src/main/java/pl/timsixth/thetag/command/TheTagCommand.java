package pl.timsixth.thetag.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.ArenaFileModel;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.configuration.type.CommandConfiguration;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.stats.model.UserStatsDbModel;
import pl.timsixth.minigameapi.util.ChatUtil;
import pl.timsixth.thetag.command.subcommand.thetag.*;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.PlayerUtil;

public class TheTagCommand extends ParentCommand {

    private final Messages messages;

    public TheTagCommand(CommandConfiguration commandConfiguration,
                         Messages messages, ArenaManager<ArenaFileModel> arenaManager, GameManager gameManager,
                         UserCoinsManager<UserCoinsDbModel> userCoinsManager, UserStatsManager<UserStatsDbModel> userStatsManager,
                         MenuManager menuManager, GameLogic gameLogic) {
        super("", true, true, false, commandConfiguration);
        this.messages = messages;
        getSubCommands().add(new ListSubCommand(arenaManager, messages));
        getSubCommands().add(new LeaveSubCommand(gameManager, messages));
        getSubCommands().add(new StatsSubCommand(userCoinsManager, messages, userStatsManager));
        getSubCommands().add(new RandomJoinSubCommand(gameLogic));
        getSubCommands().add(new CosmeticShopSubCommand(menuManager, messages));
        getSubCommands().add(new JoinSubCommand(arenaManager, gameManager, messages));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        for (String message : messages.getPlayerHelp()) {
            PlayerUtil.sendMessage(player, ChatUtil.chatColor(message));
        }

        return true;
    }
}
