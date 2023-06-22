package pl.timsixth.thetag.cosmetics.hit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class HitHeartCosmetic implements HitParticleCosmetic {

    @Override
    public String getName() {
        return "HIT_HEART";
    }

    @Override
    public void show(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Particle getParticle() {
        return Particle.HEART;
    }

    @Override
    public void show(Player player, Player entity) {
        Location location = entity.getLocation();
        double y = location.getY() + 1;
        location.setY(y);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.spawnParticle(getParticle(), location, 20));
    }
}
