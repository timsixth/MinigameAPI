package pl.timsixth.minigameapi.stats;

import pl.timsixth.minigameapi.user.User;

import java.util.UUID;
import java.util.function.ToIntFunction;

public abstract class AbstractStatistics<T extends User> implements Statistics<T> {

    protected final int getTotal(UUID uuid, ToIntFunction<? super T> mapper) {
        return getAllStatsByUuid(uuid)
                .stream()
                .mapToInt(mapper)
                .sum();
    }
}
