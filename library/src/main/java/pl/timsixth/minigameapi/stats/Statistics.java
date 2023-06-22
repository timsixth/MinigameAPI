package pl.timsixth.minigameapi.stats;

import pl.timsixth.minigameapi.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @param <T> every class which implemented {@link User}
 */
public interface Statistics<T extends User> {
    /**
     * Gets user stats for single arena
     *
     * @param uuid      user's uuid
     * @param arenaName arena name
     * @return optional of type which implemented {@link User}
     */
    Optional<T> getUser(UUID uuid, String arenaName);

    /**
     * Gets all stats for user
     *
     * @param uuid user's uuid
     * @return list of user
     */
    List<T> getAllStatsByUuid(UUID uuid);

}
