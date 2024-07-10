package pl.timsixth.exampleminigame.command.subcommand.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.minigameapi.api.module.command.SubCommand;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;

import java.util.Optional;

@RequiredArgsConstructor
public class LeaveSubCommand implements SubCommand {

    private final GameManager gameManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);
        if (!gameOptional.isPresent()) {
            player.sendMessage(messages.getNotCurrentlyPlaying());
            return true;
        }
        Game game = gameOptional.get();

        gameManager.leaveFromGame(game, player);
        player.sendMessage(messages.getLeftPlayer());

        return false;
    }

    @Override
    public String getName() {
        return "leave";
    }
}
