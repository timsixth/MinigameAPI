package pl.timsixth.minigameapi.cosmetics;

import org.bukkit.Particle;

/**
 * Represents every particle cosmetic
 */
public interface ParticleCosmetic extends Cosmetic {
    /**
     * @return particle
     */
    Particle getParticle();
}
