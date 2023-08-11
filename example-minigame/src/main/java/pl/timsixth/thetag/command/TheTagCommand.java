package pl.timsixth.thetag.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.coins.UserCoinsDbModel;
import pl.timsixth.minigameapi.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.command.ParentCommand;
import pl.timsixth.minigameapi.configuration.type.DefaultCommandConfiguration;
import pl.timsixth.minigameapi.game.GameManager;
import pl.timsixth.minigameapi.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.stats.model.UserStatsDbModel;
import pl.timsixth.minigameapi.util.ChatUtil;
import pl.timsixth.thetag.command.subcommand.thetag.*;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.game.GameLogic;
import pl.timsixth.thetag.manager.MenuManager;
import pl.timsixth.thetag.util.PlayerUtil;

public class TheTagCommand extends ParentCommand {

    private final Messages messages;

    public TheTagCommand(DefaultCommandConfiguration defaultCommandConfiguration,
                         Messages messages, ArenaManager<ArenaFileModel> arenaManager, GameManager gameManager,
                         UserCoinsManager<UserCoinsDbModel> userCoinsManager, UserStatsManager<UserStatsDbModel> userStatsManager,
                         MenuManager menuManager, GameLogic gameLogic) {
        super("", true, true, false, defaultCommandConfiguration);
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
