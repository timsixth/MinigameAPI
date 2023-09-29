package pl.timsixth.thetag.cosmetics.hit;

import org.bukkit.Particle;

public class HitSlimeCosmetic implements HitParticleCosmetic {
    @Override
    public String getName() {
        return "HIT_SLIME";
    }

    @Override
    public Particle getParticle() {
        return Particle.SLIME;
    }
}
