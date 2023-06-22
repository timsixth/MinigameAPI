package pl.timsixth.minigameapi.user;

import org.bukkit.Bukkit;
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
}
