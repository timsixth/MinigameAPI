package pl.timsixth.exampleminigame.listeners;

import org.bukkit.event.Listener;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerKickListener implements Listener {

    private final GameManager gameManager;

    @EventHandler
    private void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);
        if (!gameOptional.isPresent()) return;

        Game game = gameOptional.get();

        gameManager.leaveFromGame(game, player);
    }
}
