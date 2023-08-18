package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.config.Settings;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {


    private final Settings settings;
    private final Messages messages;
    private final GameManager gameManager;

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getPlayer().getInventory().getItemInHand().getType() != settings.getLeaveItemMaterial()) {
            return;
        }
        if (!event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(settings.getLeaveItemName())) {
            return;
        }

        Player player = event.getPlayer();

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            gameManager.leaveFromGame(game, player);
            PlayerUtil.sendMessage(player, messages.getLeftPlayer());
            event.setCancelled(true);
        }
    }
}
