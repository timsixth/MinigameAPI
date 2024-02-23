package pl.timsixth.minigameapi.api.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.game.GameManager;

/**
 * Listener which is blocking dropping items  when player is playing in game
 */
@RequiredArgsConstructor
public class PlayerDropItemListener implements Listener {

    private final GameConfiguration defaultGameConfiguration;
    private final GameManager gameManager;

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!gameManager.getGameByPlayer(event.getPlayer()).isPresent()) return;

        if (!defaultGameConfiguration.isDroppingItems()) event.setCancelled(true);
    }
}
