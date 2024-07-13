package pl.timsixth.minigameapi.api.stats.model;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.file.ConfigurationFile;
import pl.timsixth.minigameapi.api.file.SingleFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;
import pl.timsixth.minigameapi.api.model.annotations.IgnoreFields;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.util.UUID;
/**
 * Adapter for UserStats model which is saving in single YAML file
 *
 * @see SingleFile
 * @see AbstractUserStatsAdapter
 * @see UserStats
 * @see AbstractModelAdapter
 *
 * @deprecated there is new models system
 */
@SingleFile(fileName = "users_stats.yml", primarySection = "users")
@IgnoreFields
@Deprecated
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
        singleFileModel.setFiles();

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
