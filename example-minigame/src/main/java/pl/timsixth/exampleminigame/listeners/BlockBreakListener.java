package pl.timsixth.exampleminigame.listeners;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.gamestate.PlayingGameState;
import pl.timsixth.exampleminigame.gamestate.WinGameState;
import pl.timsixth.exampleminigame.model.MyUserGame;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.minigameapi.api.game.user.UserGame;
import pl.timsixth.minigameapi.api.stats.manager.UserStatsManager;

import java.util.Optional;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final Settings settings;
    private final GameManager gameManager;
    private final Messages messages;
    private final UserStatsManager userStatsManager;
    private final UserCoinsManager userCoinsManager;

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);

        if (!gameOptional.isPresent()) return;

        Game game = gameOptional.get();

        if (!(game.getState() instanceof PlayingGameState)) {
            event.setCancelled(true);
            return;
        }

        Optional<UserGame> userGameOptional = game.getUserGame(player.getUniqueId());

        if (!userGameOptional.isPresent()) return;

        MyUserGame myUserGame = (MyUserGame) userGameOptional.get();

        myUserGame.addBlock();

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(messages.getYouHaveBrokenBlocks().replace("{BLOCKS_AMOUNT}", String.valueOf(myUserGame.getBrokeBlocksAmount()))));

        if (settings.getNeededBlockToWin() != myUserGame.getBrokeBlocksAmount()) return;

        game.setState(new WinGameState(game, messages, userStatsManager, settings, gameManager, userCoinsManager));
    }
}
