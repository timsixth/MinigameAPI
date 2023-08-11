package pl.timsixth.thetag.command.subcommand.thetagadmin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.ArenaImpl;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.command.SubCommand;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.HashMap;

@RequiredArgsConstructor
public class CreateSubCommand implements SubCommand {

    private final ArenaManager<ArenaFileModel> arenaManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 2){
            Player player = (Player) sender;

            if (arenaManager.getArena(args[1]).isPresent()) {
                PlayerUtil.sendMessage(player, messages.getArenaExists());
                return true;
            }
            arenaManager.addArena(new ArenaImpl(args[1], player.getLocation(), new HashMap<>()));
            PlayerUtil.sendMessage(player, messages.getArenaCreated());
        }

        return false;
    }

    @Override
    public String getName() {
        return "create";
    }
}