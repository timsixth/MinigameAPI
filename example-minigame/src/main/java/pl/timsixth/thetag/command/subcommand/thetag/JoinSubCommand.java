package pl.timsixth.thetag.command.subcommand.thetag;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.command.SubCommand;
import pl.timsixth.minigameapi.game.GameManager;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;
@RequiredArgsConstructor
public class JoinSubCommand implements SubCommand {

    private final ArenaManager<ArenaFileModel> arenaManager;
    private final GameManager gameManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 2){
            Player player = (Player) sender;

            Optional<ArenaFileModel> arenaOptional = arenaManager.getArena(args[1]);
            if (!arenaOptional.isPresent()) {
                PlayerUtil.sendMessage(player, messages.getArenaDoesNotExists());
                return true;
            }
            gameManager.joinGame(arenaOptional.get(), player);
        }

        return false;
    }

    @Override
    public String getName() {
        return "join";
    }
}
