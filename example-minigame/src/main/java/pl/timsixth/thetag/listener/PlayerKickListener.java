package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import pl.timsixth.thetag.game.GameLogic;

@RequiredArgsConstructor
public class PlayerKickListener implements Listener {

    private final GameLogic gameLogic;

    @EventHandler
    private void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        gameLogic.leaveFromGame(player);
    }

}
