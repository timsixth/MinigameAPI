package pl.timsixth.minigameapi.api.stats.factory;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.stats.model.SingleYamlFileUserStats;
import pl.timsixth.minigameapi.api.stats.model.UserStats;

import java.util.UUID;

public class UserStatsFactoryImpl implements UserStatsFactory {
    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName, int wins, int defeats) {
        if (!MiniGame.getInstance().getPluginConfiguration().isUseDefaultStatsSystem()) {
            MiniGame.getInstance().getLogger().info("Default stats system is disabled");
            return null;
        }

        return new SingleYamlFileUserStats(uuid, name, arenaName, wins, defeats);
    }

    @Override
    public UserStats createUserStats(UUID uuid, String name, String arenaName) {
        return createUserStats(uuid, name, arenaName, 0, 0);
    }
}
