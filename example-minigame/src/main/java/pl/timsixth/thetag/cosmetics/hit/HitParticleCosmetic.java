package pl.timsixth.thetag.cosmetics.hit;

import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.cosmetics.ParticleCosmetic;

public interface HitParticleCosmetic extends ParticleCosmetic {

    void show(Player player, Player entity);
}
