package pl.timsixth.minigameapi.api.stats.factory;

import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

public interface UserStatsFactory {

    UserStats createUserStats(UUID uuid, String name, String arenaName, int wins, int defeats);
    UserStats createUserStats(UUID uuid, String name, String arenaName);

}
