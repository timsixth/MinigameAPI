package pl.timsixth.minigameapi.api.stats.loader;

import org.bukkit.configuration.ConfigurationSection;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.loader.file.AbstractFileLoader;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.util.UUID;

public class UserStatsSingleFileLoader extends AbstractFileLoader<UserStats> implements UserStatsLoader {
    @Override
    public void load() {
        load("users_stats.yml", "users");
    }

    @Override
    public void load(String fileName, String primarySection) {
        loadFile(fileName, primarySection, (uuid, section) -> {
            ConfigurationSection uuidSection = ConfigurationSectionUtil.getSection(section, uuid);

            String name = uuidSection.getString("name");

            for (String arenaName : uuidSection.getKeys(false)) {
                ConfigurationSection arenaNameSection = ConfigurationSectionUtil.getSection(uuidSection, arenaName);

                UserStats userStats = MiniGame.getUserStatsFactory().createUserStats(
                        UUID.fromString(uuid),
                        name,
                        arenaName,
                        arenaNameSection.getInt("wins"),
                        arenaNameSection.getInt("defeats")
                );

                this.addObject(userStats);
            }

        });
    }
}
