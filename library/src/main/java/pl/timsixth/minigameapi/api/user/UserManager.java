package pl.timsixth.minigameapi.api.user;

import java.util.List;
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

    /**
     * Gets list of users
     *
     * @return list of users
     */
    List<T> getUsers();

    /**
     * Adds new user
     *
     * @param user every class which implemented {@link User}
     */
    void addUser(T user);

    /**
     * Removes user
     *
     * @param user every class which implemented {@link User}
     */
    void removeUser(T user);
}
