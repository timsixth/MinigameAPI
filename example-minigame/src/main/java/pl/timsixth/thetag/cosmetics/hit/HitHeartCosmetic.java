package pl.timsixth.thetag.cosmetics.hit;

import org.bukkit.Particle;

public class HitHeartCosmetic implements HitParticleCosmetic {

    @Override
    public String getName() {
        return "HIT_HEART";
    }

    @Override
    public Particle getParticle() {
        return Particle.HEART;
    }
}
