package pl.timsixth.thetag.command.subcommand.thetagadmin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.ArenaFileModel;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;
@RequiredArgsConstructor
public class SetSpawnSubCommand extends SubCommand {

    private final ArenaManager<ArenaFileModel> arenaManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player player = (Player) sender;

            Optional<ArenaFileModel> arenaOptional = arenaManager.getArena(args[1]);
            if (!arenaOptional.isPresent()) {
                PlayerUtil.sendMessage(player, messages.getArenaDoesNotExists());
                return true;
            }
            ArenaFileModel arena = arenaOptional.get();
            arena.addLocation("spawn", player.getLocation());

            arena.update();

            PlayerUtil.sendMessage(player, messages.getSetGameSpawn());
        }

        return false;
    }

    @Override
    public String getName() {
        return "setspawn";
    }
}
