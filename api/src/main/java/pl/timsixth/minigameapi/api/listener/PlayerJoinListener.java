package pl.timsixth.minigameapi.api.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;

import java.util.Optional;

/**
 * Listener which is adding user coins on join
 */
@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final UserCoinsManager userCoinsManager;

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Optional<UserCoins> userCoinsDbModelOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
        if (!userCoinsDbModelOptional.isPresent()) {
            UserCoins userCoins = MiniGame.getUserCoinsFactory().createUserCoins(player.getUniqueId());

            userCoinsManager.addUser(userCoins);

            System.out.println(userCoins);

            userCoins.save();
        }
    }

}
