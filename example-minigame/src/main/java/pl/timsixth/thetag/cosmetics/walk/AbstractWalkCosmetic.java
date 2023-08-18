package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.cosmetics.ParticleCosmetic;

public abstract class AbstractWalkCosmetic implements ParticleCosmetic {

    @Override
    public void show(Player player) {
        Location location = player.getLocation();
        double y = location.getY() + 2;

        location.setY(y);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.spawnParticle(getParticle(), location, 20));
    }
}
