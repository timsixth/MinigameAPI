package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import pl.timsixth.minigameapi.api.game.GameManager;

@RequiredArgsConstructor
public class FoodLevelChangeListener implements Listener {

    private final GameManager gameManager;

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        if (!gameManager.getGameByPlayer(player).isPresent()) return;

        event.setCancelled(true);
    }
}
