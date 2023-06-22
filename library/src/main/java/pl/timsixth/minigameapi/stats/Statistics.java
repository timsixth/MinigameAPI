package pl.timsixth.minigameapi.stats;

import pl.timsixth.minigameapi.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Statistics<T extends User> {

    Optional<T> getUser(UUID uuid, String arenaName);

    List<T> getAllStatsByUuid(UUID uuid);

}
