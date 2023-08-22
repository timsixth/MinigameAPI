package pl.timsixth.thetag.command.subcommand.thetag;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.thetag.game.GameLogic;

@RequiredArgsConstructor
public class RandomJoinSubCommand implements SubCommand {

    private final GameLogic gameLogic;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        gameLogic.randomJoin(player);

        return false;
    }

    @Override
    public String getName() {
        return "randomjoin";
    }
}
