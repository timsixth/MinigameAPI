package pl.timsixth.minigameapi.api.user;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Represents every user
 */
public interface User {
    /**
     * @return user's uuid
     */
    UUID getUuid();

    /**
     * Converts user to player
     *
     * @return bukkit player
     */
    default Player toPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    /**
     * Converts user to offline player
     *
     * @return bukkit offline player
     */
    default OfflinePlayer toOfflinePlayer() {
        return Bukkit.getOfflinePlayer(getUuid());
    }
}
