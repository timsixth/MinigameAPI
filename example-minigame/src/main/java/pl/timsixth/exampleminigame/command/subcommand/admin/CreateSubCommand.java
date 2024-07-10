package pl.timsixth.exampleminigame.command.subcommand.admin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.arena.factory.ArenaFactory;
import pl.timsixth.minigameapi.api.arena.manager.ArenaManager;
import pl.timsixth.minigameapi.api.module.command.SubCommand;

@RequiredArgsConstructor
public class CreateSubCommand implements SubCommand {

    private final ArenaManager arenaManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player player = (Player) sender;

            if (arenaManager.getArena(args[1]).isPresent()) {
                player.sendMessage(messages.getArenaExists());
                return true;
            }
            ArenaFactory arenaFactory = MiniGame.getArenaFactory();
            Arena arena = arenaFactory.createArena(args[1], player.getLocation());
            arena.options().setValue("option", 123); //set custom option
            arenaManager.addArena(arena);
            player.sendMessage(messages.getArenaCreated());
        }

        return false;
    }

    @Override
    public String getName() {
        return "create";
    }
}
