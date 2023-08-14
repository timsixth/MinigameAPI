package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Particle;

public class WalkDripLavaCosmetic extends AbstractWalkCosmetic {
    @Override
    public String getName() {
        return "WALK_DRIP_LAVA";
    }

    @Override
    public Particle getParticle() {
        return Particle.DRIP_LAVA;
    }
}
