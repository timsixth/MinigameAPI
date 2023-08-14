package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Particle;

public class WalkSlimeCosmetic extends AbstractWalkCosmetic {
    @Override
    public String getName() {
        return "WALK_SLIME";
    }

    @Override
    public Particle getParticle() {
        return Particle.SLIME;
    }
}
