package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.cosmetics.ParticleCosmetic;

public class WalkHeartCosmetic implements ParticleCosmetic {

    @Override
    public String getName() {
        return "WALK_HEART";
    }

    @Override
    public void show(Player player) {
        Location location = player.getLocation();
        double y = location.getY() + 2;

        location.setY(y);

        player.spawnParticle(getParticle(), location, 20);
    }

    @Override
    public Particle getParticle() {
        return Particle.HEART;
    }
}
