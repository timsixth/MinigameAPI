package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.timsixth.thetag.game.GameLogic;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

    private final GameLogic gameLogic;

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        gameLogic.leaveFromGame(player);
    }
}
