package pl.timsixth.minigameapi.user;

import java.util.Optional;
import java.util.UUID;

public interface UserManager<T extends User> {

    Optional<T> getUserByUuid(UUID uuid);
}
