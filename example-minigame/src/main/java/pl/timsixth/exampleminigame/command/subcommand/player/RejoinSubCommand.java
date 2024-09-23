package pl.timsixth.exampleminigame.command.subcommand.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.exampleminigame.gamestate.PlayingGameState;
import pl.timsixth.exampleminigame.model.MyUserGame;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;
import pl.timsixth.minigameapi.api.game.user.converter.UserGameConverter;
import pl.timsixth.minigameapi.api.module.command.SubCommand;

import java.util.Optional;

@RequiredArgsConstructor
public class RejoinSubCommand implements SubCommand {

    private final GameManager gameManager;

    /*
    Example of implementation of rejoin feature
     */
    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (gameManager.getGameByPlayer(player).isPresent()) {
            player.sendMessage("You can not rejoin because, you are already playing in the game");
            return true;
        }

        Optional<Game> gameOptional = gameManager.rejoin(player, (game) -> game.getState() instanceof PlayingGameState, (rejoinState) -> {
            UserGameConverter userGameConverter = gameManager.getUserGameConverter();

            RecoverableUserGame recoverableUserGame = rejoinState.getRecoverableUserGame();
            Game game = rejoinState.getGame();

            MyUserGame myUserGame = userGameConverter.convertToUserGame(recoverableUserGame, MyUserGame.class);

            game.addUserGame(myUserGame);

            rejoinState.teleportToLatestLocation(player);

            rejoinState.restorePlayerOptions(player);
            rejoinState.restorePlayerInventory(player);

            game.removeRecoverableUserGame(recoverableUserGame);
        });

        gameOptional.ifPresent(game -> player.sendMessage("You have rejoined to: " + game.getArena().getName()));

        gameManager.getGameByPlayer(player).flatMap(game -> game.getUserGame(player.getUniqueId()))
                .filter(userGame -> userGame instanceof MyUserGame)
                .ifPresent(userGame -> {
                    MyUserGame myUserGame = (MyUserGame) userGame;

                    player.sendMessage("Broken blocks: " + myUserGame.getBrokeBlocksAmount());
                });

        return false;
    }

    @Override
    public String getName() {
        return "rejoin";
    }
}
