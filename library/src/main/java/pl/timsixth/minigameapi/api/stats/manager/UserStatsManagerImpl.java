package pl.timsixth.minigameapi.api.stats.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.arena.Arena;
import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link UserStatsManager}
 *
 * @see UserStats
 * @see AbstractUserStatsManager
 */
@RequiredArgsConstructor
public class UserStatsManagerImpl extends AbstractUserStatsManager {

    private final Loader<UserStats> userStatsLoader;

    @Override
    public Optional<UserStats> getUser(UUID uuid, String arenaName) {
        return userStatsLoader.getData()
                .stream().filter(userStats -> userStats.getUuid().equals(uuid))
                .filter(userStats -> userStats.getArenaName().equalsIgnoreCase(arenaName))
                .findAny();
    }

    @Override
    public List<UserStats> getAllStatsByUuid(UUID uuid) {
        return userStatsLoader.getData().stream()
                .filter(userStats -> userStats.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    @Override
    public void addNewUser(UserStats userStats) {
        throw new UnsupportedOperationException("This method is deprecated");
    }

    @Override
    public UserStats getUserStatsOrCreate(Player player, Arena arena) {
        Optional<UserStats> userStatsOptional = getUser(player.getUniqueId(), arena.getName());

        UserStats userStats;
        if (!userStatsOptional.isPresent()) {
            userStats = MiniGame.getUserStatsFactory().createUserStats(player.getUniqueId(), player.getName(), arena.getName());

            userStatsLoader.addObject(userStats);

            userStats.save();
        } else {
            userStats = userStatsOptional.get();
        }

        return userStats;
    }
}
