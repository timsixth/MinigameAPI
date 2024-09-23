package pl.timsixth.minigameapi.api.stats.dao;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.file.ConfigurationFile;
import pl.timsixth.minigameapi.api.file.SingleYamlFileDao;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.stats.model.UserStats;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

public class SingleYamlFileUserStatsDao extends SingleYamlFileDao {

    private final ConfigurationFile configurationFile;

    private final UserStats userStats;

    public SingleYamlFileUserStatsDao(Model model) {
        super(model);

        this.userStats = (UserStats) model;
        this.configurationFile = getConfigurationFile();
    }

    @Override
    public Object save(Model model) {
        this.createFile();

        return this.update(model);
    }

    @Override
    public Object update(Model model) {
        setFiles();

        YamlConfiguration yamlConfiguration = configurationFile.getYamlConfiguration();

        ConfigurationSection uuidSection = ConfigurationSectionUtil.getSection(yamlConfiguration, "users." + userStats.getUuid().toString());

        if (!uuidSection.contains(userStats.getArenaName())) uuidSection.createSection(userStats.getArenaName());

        ConfigurationSection arenaNameSection = uuidSection.getConfigurationSection(userStats.getArenaName());

        arenaNameSection.set("wins", userStats.getWins());
        arenaNameSection.set("defeats", userStats.getDefeats());

        configurationFile.saveFile();

        return model;
    }
}
