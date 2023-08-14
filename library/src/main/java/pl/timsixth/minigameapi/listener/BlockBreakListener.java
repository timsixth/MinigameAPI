package pl.timsixth.minigameapi.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.timsixth.minigameapi.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.game.GameManager;

/**
 * Listener which is blocking breaking blocks when player is playing in game
 */
@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final GameConfiguration defaultGameConfiguration;
    private final GameManager gameManager;

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        if (!gameManager.getGameByPlayer(event.getPlayer()).isPresent()) return;

        if (!defaultGameConfiguration.isBlockBreaking()) event.setCancelled(true);
    }
}
