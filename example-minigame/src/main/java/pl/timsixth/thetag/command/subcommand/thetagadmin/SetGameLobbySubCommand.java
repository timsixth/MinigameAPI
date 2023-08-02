package pl.timsixth.thetag.command.subcommand.thetagadmin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.arena.ArenaFileModel;
import pl.timsixth.minigameapi.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.command.SubCommand;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;
@RequiredArgsConstructor
public class SetGameLobbySubCommand implements SubCommand {

    private final ArenaManager<ArenaFileModel> arenaManager;
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
            ArenaFileModel arena = arenaOptional.get();

            arena.setLobbyLocation(player.getLocation());
            arena.update();

            PlayerUtil.sendMessage(player, messages.getSetLobbySpawn());
        }

        return false;
    }

    @Override
    public String getName() {
        return "setgamelobby";
    }
}
