package pl.timsixth.exampleminigame.command.subcommand.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.module.command.SubCommand;
import pl.timsixth.minigameapi.api.game.GameManager;

import java.util.Optional;
@RequiredArgsConstructor
public class JoinSubCommand implements SubCommand {

    private final ArenaManager arenaManager;
    private final GameManager gameManager;
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
            gameManager.joinGame(arenaOptional.get(), player);
        }

        return false;
    }

    @Override
    public String getName() {
        return "join";
    }
}
