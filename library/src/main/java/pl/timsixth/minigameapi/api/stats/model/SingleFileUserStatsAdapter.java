package pl.timsixth.minigameapi.api.stats.model;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.file.ConfigurationFile;
import pl.timsixth.minigameapi.api.file.SingleFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.model.InitializableModel;
import pl.timsixth.minigameapi.api.model.annoations.IgnoreFields;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.io.File;
import java.util.UUID;

@SingleFile(fileName = "users_stats.yml", primarySection = "users")
@IgnoreFields
public class SingleFileUserStatsAdapter extends AbstractUserStatsAdapter {

    private final ConfigurationFile configurationFile;
    private final SingleFileModel singleFileModel;

    public SingleFileUserStatsAdapter(InitializableModel model, UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(model, uuid, name, arenaName, wins, defeats);
        singleFileModel = (SingleFileModel) model;

        model.init(this);

        configurationFile = singleFileModel.getConfigurationFile();
    }

    @Override
    public Object save() {
        singleFileModel.createFile();

        return this.update();
    }

    @Override
    public Object update() {
        File file = new File(MiniGame.getInstance().getDataFolder(), configurationFile.getName());
        configurationFile.setFile(file);
        configurationFile.setYamlConfiguration(YamlConfiguration.loadConfiguration(file));

        YamlConfiguration yamlConfiguration = configurationFile.getYamlConfiguration();

        ConfigurationSection uuidSection = ConfigurationSectionUtil.getSection(yamlConfiguration, "users." + getUuid().toString());

        if (!uuidSection.contains(getArenaName())) uuidSection.createSection(getArenaName());

        ConfigurationSection arenaNameSection = uuidSection.getConfigurationSection(getArenaName());

        arenaNameSection.set("wins", getWins());
        arenaNameSection.set("defeats", getDefeats());

        configurationFile.saveFile();

        return model;
    }

}
