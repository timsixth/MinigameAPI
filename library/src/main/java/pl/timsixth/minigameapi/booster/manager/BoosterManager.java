package pl.timsixth.minigameapi.booster.manager;

import pl.timsixth.minigameapi.booster.Booster;

import java.util.List;
import java.util.Optional;

public interface BoosterManager<T extends Booster> {

    Optional<T> getBoosterByName(String name);

    void addBooster(T booster);

    void removeBooster(T booster);

    List<T> getBoosters();
}
