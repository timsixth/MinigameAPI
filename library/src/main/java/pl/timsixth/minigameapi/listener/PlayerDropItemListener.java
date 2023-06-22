package pl.timsixth.minigameapi.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.timsixth.minigameapi.configuration.type.DefaultGameConfiguration;
import pl.timsixth.minigameapi.game.GameManager;

@RequiredArgsConstructor
public class PlayerDropItemListener implements Listener {

    private final DefaultGameConfiguration defaultGameConfiguration;
    private final GameManager gameManager;

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!gameManager.getGameByPlayer(event.getPlayer()).isPresent()) return;

        if (!defaultGameConfiguration.isDroppingItems()) event.setCancelled(true);
    }
}
