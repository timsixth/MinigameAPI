package pl.timsixth.thetag.command.subcommand.thetag;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.PlayerUtil;

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
            PlayerUtil.sendMessage(player, messages.getNotCurrentlyPlaying());
            return true;
        }
        Game game = gameOptional.get();

        gameManager.leaveFromGame(game, player);
        PlayerUtil.sendMessage(player, messages.getLeftPlayer());

        return false;
    }

    @Override
    public String getName() {
        return "leave";
    }
}
