package pl.timsixth.minigameapi.file;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.util.FileUtil;

public abstract class ManyFilesModel extends AbstractFileModel {

    @Override
    public boolean delete() {
        getConfigurationFile().setFile(null);
        getConfigurationFile().setYamlConfiguration(null);

        return FileUtil.deleteFile(getConfigurationFile());
    }

    @Override
    public Object update() {
        YamlConfiguration yamlConfiguration = getConfigurationFile().getYamlConfiguration();
        yamlConfiguration.set(getConfigurationFile().getIdSection().toString(), this);

        getConfigurationFile().saveFile();

        return this;
    }

    @Override
    public Object save() {
        super.save();

        return this.update();
    }
}
