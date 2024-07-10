package pl.timsixth.exampleminigame.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.command.subcommand.player.*;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.tabcompleter.ExampleMiniGameCommandTabCompleter;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.module.command.ParentCommand;
import pl.timsixth.minigameapi.api.module.command.tabcompleter.BaseTabCompleter;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;
import pl.timsixth.minigameapi.api.util.ChatUtil;

public class ExampleMiniGameCommand extends ParentCommand {

    private final Messages messages;
    private final ArenaManager arenaManager;

    public ExampleMiniGameCommand(Messages messages, ArenaManager arenaManager, GameManager gameManager,
                                  UserCoinsManager userCoinsManager, UserStatsManager userStatsManager, UserCosmeticsManager userCosmeticsManager, CosmeticsManager cosmeticsManager) {
        super("", true, true, false);
        this.messages = messages;
        this.arenaManager = arenaManager;
        addSubCommand(new ListSubCommand(arenaManager, messages));
        addSubCommand(new LeaveSubCommand(gameManager, messages));
        addSubCommand(new StatsSubCommand(userCoinsManager, messages, userStatsManager));
        addSubCommand(new JoinSubCommand(arenaManager, gameManager, messages));
        addSubCommand(new CosmeticsSubCommand(userCosmeticsManager, cosmeticsManager, userCoinsManager));
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        for (String message : messages.getPlayerHelp()) {
            player.sendMessage(ChatUtil.chatColor(message));
        }

        return true;
    }

    @Override
    public String getName() {
        return "em";
    }

    @Override
    public BaseTabCompleter getTabCompleter() {
        return new ExampleMiniGameCommandTabCompleter(this, arenaManager);
    }
}
