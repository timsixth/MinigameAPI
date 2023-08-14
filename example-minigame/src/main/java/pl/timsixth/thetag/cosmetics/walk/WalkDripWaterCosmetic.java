package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Particle;

public class WalkDripWaterCosmetic extends AbstractWalkCosmetic {
    @Override
    public String getName() {
        return "WALK_DRIP_WATER";
    }

    @Override
    public Particle getParticle() {
        return Particle.DRIP_WATER;
    }
}
