package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Particle;

public class WalkHeartCosmetic implements WalkCosmetic {

    @Override
    public String getName() {
        return "WALK_HEART";
    }

    @Override
    public Particle getParticle() {
        return Particle.HEART;
    }
}
