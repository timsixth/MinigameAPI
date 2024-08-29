package pl.timsixth.minigameapi.api.game.user.rejoin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.user.RecoverableUserGame;

/**
 * Represents data when rejoin has been accepted to execute
 */
@RequiredArgsConstructor
@Getter
public class RejoinState {

    private final Game game;
    private final RecoverableUserGame recoverableUserGame;

    /**
     * Restores player options like health, exp, food level, level, fire ticks
     *
     * @param player player to restore options
     */
    public void restorePlayerOptions(Player player) {
        player.setHealth(recoverableUserGame.getHealth());
        player.setExp(recoverableUserGame.getExperience());
        player.setFoodLevel(recoverableUserGame.getFoodLevel());
        player.setLevel(recoverableUserGame.getLevel());
        player.setFireTicks(recoverableUserGame.getFireTicks());
    }

    /**
     * Restores player's inventory (Armor and storage)
     *
     * @param player player to restore inventory
     */
    public void restorePlayerInventory(Player player) {
        player.getInventory().setArmorContents(recoverableUserGame.getArmor());

        for (int i = 0; i < recoverableUserGame.getContents().length; i++) {
            player.getInventory().setItem(i, recoverableUserGame.getContents()[i]);
        }
    }

    /**
     * Teleports player to location before leave from arena
     *
     * @param player player to teleport to latest location
     */
    public void teleportToLatestLocation(Player player) {
        player.teleport(recoverableUserGame.getLatestLocation());
    }
}
