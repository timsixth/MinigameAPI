package pl.timsixth.exampleminigame.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {


    private final Settings settings;
    private final Messages messages;
    private final GameManager gameManager;

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (itemInMainHand.getType() == Material.AIR) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (itemInMainHand.getType() != settings.getLeaveItemMaterial()) {
            return;
        }
        if (!itemInMainHand.getItemMeta().getDisplayName().equalsIgnoreCase(settings.getLeaveItemName())) {
            return;
        }

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            gameManager.leaveFromGame(game, player);

            player.sendMessage(messages.getLeftPlayer());
            event.setCancelled(true);
        }
    }
}
