package pl.timsixth.minigameapi.api.stats;

import pl.timsixth.minigameapi.api.user.User;

import java.util.UUID;
import java.util.function.ToIntFunction;

/**
 * Template method for {@link Statistics}
 *
 * @param <T> every class which implemented {@link User}
 */
public abstract class AbstractStatistics<T extends User> implements Statistics<T> {
    /**
     * Get total amount of numerical stat
     *
     * @param uuid   user's uuid
     * @param mapper mapper to get total amount of numerical stat
     * @return total amount of numerical stat
     */
    protected final int getTotal(UUID uuid, ToIntFunction<? super T> mapper) {
        return getAllStatsByUuid(uuid)
                .stream()
                .mapToInt(mapper)
                .sum();
    }
}
