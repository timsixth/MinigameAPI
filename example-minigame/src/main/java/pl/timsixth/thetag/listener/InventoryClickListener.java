package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.timsixth.minigameapi.api.game.GameManager;

@RequiredArgsConstructor
public class InventoryClickListener implements Listener {

    private final GameManager gameManager;

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!gameManager.getGameByPlayer(player).isPresent()) return;

        event.setCancelled(true);
    }
}
