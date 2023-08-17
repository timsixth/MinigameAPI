package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;
import pl.timsixth.thetag.cosmetics.CosmeticCategory;
import pl.timsixth.thetag.game.GameLogic;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

    private final GameManager gameManager;
    private final GameLogic gameLogic;

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);

        if (!gameOptional.isPresent()) return;

        gameLogic.showCosmetics(player, null, CosmeticCategory.WALK.name());
    }
}
