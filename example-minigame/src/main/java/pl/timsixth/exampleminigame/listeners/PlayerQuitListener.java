package pl.timsixth.exampleminigame.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.GameManager;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

    private final GameManager gameManager;

    @EventHandler
    private void onKick(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Optional<Game> gameOptional = gameManager.getGameByPlayer(player);
        if (!gameOptional.isPresent()) return;

        Game game = gameOptional.get();

        gameManager.leaveFromGame(game, player);
    }
}
