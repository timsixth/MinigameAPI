package pl.timsixth.thetag.cosmetics.hit;

import org.bukkit.Particle;

public class HitSnowCosmetic implements HitParticleCosmetic {
    @Override
    public String getName() {
        return "HIT_SNOW";
    }

    @Override
    public Particle getParticle() {
        return Particle.SNOWBALL;
    }
}
