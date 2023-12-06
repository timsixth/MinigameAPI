package pl.timsixth.thetag.cosmetics.walk;

import org.bukkit.Particle;

public class WalkSnowCosmetic implements WalkCosmetic{
    @Override
    public String getName() {
        return "WALK_SNOW";
    }

    @Override
    public Particle getParticle() {
        return Particle.SNOWBALL;
    }
}
