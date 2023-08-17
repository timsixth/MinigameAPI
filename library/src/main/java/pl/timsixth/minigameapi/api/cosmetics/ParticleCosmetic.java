package pl.timsixth.minigameapi.api.cosmetics;

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
