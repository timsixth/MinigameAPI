package pl.timsixth.thetag.cosmetics.walk;

import pl.timsixth.minigameapi.api.cosmetics.ParticleCosmetic;

interface WalkCosmetic extends ParticleCosmetic {

    @Override
    default int getParticleHeight() {
        return 2;
    }
}
