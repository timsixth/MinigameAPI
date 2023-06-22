package pl.timsixth.minigameapi.user;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents every user manager
 *
 * @param <T> every class which implemented {@link User}
 */
public interface UserManager<T extends User> {
    /**
     * Gets user by uuid
     *
     * @param uuid user's uuid
     * @return Optional of user
     */
    Optional<T> getUserByUuid(UUID uuid);
}
