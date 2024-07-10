package pl.timsixth.exampleminigame.command.subcommand.admin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.module.command.SubCommand;
import pl.timsixth.exampleminigame.config.Messages;

import java.util.Optional;
@RequiredArgsConstructor
public class SetGameLobbySubCommand implements SubCommand {

    private final ArenaManager arenaManager;
    private final Messages messages;
    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 2){
            Player player = (Player) sender;

            Optional<Arena> arenaOptional = arenaManager.getArena(args[1]);
            if (!arenaOptional.isPresent()) {
                player.sendMessage(messages.getArenaDoesNotExists());
                return true;
            }
            Arena arena = arenaOptional.get();

            arena.setLobbyLocation(player.getLocation());
            arena.update();

            player.sendMessage(messages.getSetLobbySpawn());
        }

        return false;
    }

    @Override
    public String getName() {
        return "setgamelobby";
    }
}
