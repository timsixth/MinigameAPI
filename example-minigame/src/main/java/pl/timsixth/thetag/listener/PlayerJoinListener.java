package pl.timsixth.thetag.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.UserCoinsImpl;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final UserCoinsManager userCoinsManager;

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Optional<UserCoins> userCoinsDbModelOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
        if (!userCoinsDbModelOptional.isPresent()) {
            UserCoins userCoinsDbModel = new UserCoinsImpl(player.getUniqueId(), 0);

            userCoinsManager.addUser(userCoinsDbModel);

            userCoinsDbModel.save();
        }
    }

}
