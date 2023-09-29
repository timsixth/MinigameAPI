package pl.timsixth.thetag.cosmetics.hit;

import org.bukkit.Particle;

public class HitNoteCosmetic implements HitParticleCosmetic {
    @Override
    public String getName() {
        return "HIT_NOTE";
    }

    @Override
    public Particle getParticle() {
        return Particle.NOTE;
    }
}
