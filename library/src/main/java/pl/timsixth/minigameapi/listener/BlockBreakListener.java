package pl.timsixth.minigameapi.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.timsixth.minigameapi.configuration.type.DefaultGameConfiguration;
import pl.timsixth.minigameapi.game.GameManager;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final DefaultGameConfiguration defaultGameConfiguration;
    private final GameManager gameManager;

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        if (!gameManager.getGameByPlayer(event.getPlayer()).isPresent()) return;

        if (!defaultGameConfiguration.isBlockBreaking()) event.setCancelled(true);
    }
}
