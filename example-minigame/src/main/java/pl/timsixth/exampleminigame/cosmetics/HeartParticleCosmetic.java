package pl.timsixth.exampleminigame.cosmetics;

import org.bukkit.Particle;
import pl.timsixth.minigameapi.api.cosmetics.ParticleCosmetic;

/*
Sample particle cosmetic
 */
public class HeartParticleCosmetic implements ParticleCosmetic {

    @Override
    public String getName() {
        return "WALK_HEART";
    }

    @Override
    public Particle getParticle() {
        return Particle.HEART;
    }

    @Override
    public int getParticleHeight() {
        return 2;
    }
}
