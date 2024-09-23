package pl.timsixth.minigameapi.api.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.util.ConfigurationSectionUtil;

import java.io.File;

public class SingleYamlFileDao extends AbstractYamlFileDao {

    public SingleYamlFileDao(Model model) {
        super(model);
    }

    @Override
    public Object save(Model model) {
        super.save(model);

        return this.update(model);
    }

    @Override
    public boolean delete(Model model) {
        setFiles();

        YamlConfiguration yamlConfiguration = getConfigurationFile().getYamlConfiguration();
        ConfigurationSection primarySection = ConfigurationSectionUtil.getSection(yamlConfiguration, getConfigurationFile().getPrimarySection());
        String sectionId = getConfigurationFile().getIdSection().toString();

        if (primarySection.contains(sectionId)) {
            primarySection.set(sectionId, null);
            getConfigurationFile().saveFile();
            return true;
        }

        return false;
    }

    @Override
    public Object update(Model model) {
        setFiles();

        ConfigurationFile configurationFile = getConfigurationFile();

        YamlConfiguration yamlConfiguration = configurationFile.getYamlConfiguration();
        ConfigurationSection primarySection = ConfigurationSectionUtil.getSection(yamlConfiguration, configurationFile.getPrimarySection());
        primarySection.set(configurationFile.getIdSection().toString(), model);

        configurationFile.saveFile();

        return model;
    }

    /**
     * Sets new value of file and yaml configuration
     */
    public void setFiles() {
        File file = new File(MiniGame.getInstance().getDataFolder(), getConfigurationFile().getName());
        getConfigurationFile().setFile(file);
        getConfigurationFile().setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
    }
}
