package pl.timsixth.minigameapi.api.cosmetics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

/**
 * Represents every particle cosmetic
 */
public interface ParticleCosmetic extends Cosmetic {
    /**
     * @return particle
     */
    Particle getParticle();

    default void show(Player player) {
        Location location = player.getLocation();
        double y = location.getY() + getParticleHeight();
        location.setY(y);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.spawnParticle(getParticle(), location, 20));
    }

    /**
     * Gets height to add to y coordinate for particle's spawn location
     *
     * @return the number which will add to the y coordinate
     */
    int getParticleHeight();
}
