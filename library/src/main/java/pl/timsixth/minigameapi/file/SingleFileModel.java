package pl.timsixth.minigameapi.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.MiniGame;
import pl.timsixth.minigameapi.util.ConfigurationSectionUtil;

import java.io.File;

public abstract class SingleFileModel extends AbstractFileModel {

    @Override
    public boolean delete() {
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
    public Object update() {
        setFiles();

        YamlConfiguration yamlConfiguration = getConfigurationFile().getYamlConfiguration();
        ConfigurationSection primarySection = ConfigurationSectionUtil.getSection(yamlConfiguration, getConfigurationFile().getPrimarySection());
        primarySection.set(getConfigurationFile().getIdSection().toString(), this);

        getConfigurationFile().saveFile();

        return this;
    }

    private void setFiles() {
        if (getConfigurationFile().getYamlConfiguration() == null) {
            File file = new File(MiniGame.getInstance().getDataFolder(), getConfigurationFile().getName());
            getConfigurationFile().setFile(file);
            getConfigurationFile().setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
        }
    }

    @Override
    public Object save() {
        super.save();

        return this.update();
    }
}
