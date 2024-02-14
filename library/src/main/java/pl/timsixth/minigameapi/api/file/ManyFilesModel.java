package pl.timsixth.minigameapi.api.file;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.util.FileUtil;

import java.io.File;

/**
 * Represents every file model which is saving in many files
 *
 * @see FileModel
 * @see AbstractFileModel
 */
public abstract class ManyFilesModel extends AbstractFileModel {

    @Override
    public Object save(Model model) {
        createFile();

        return this.update();
    }

    @Override
    public boolean delete(Model model) {
        getConfigurationFile().setFile(null);
        getConfigurationFile().setYamlConfiguration(null);

        return FileUtil.deleteFile(getConfigurationFile());
    }

    @Override
    public Object update(Model model) {
        YamlConfiguration yamlConfiguration = getConfigurationFile().getYamlConfiguration();

        String path = getConfigurationFile().getIdSection().toString();

        if (!getConfigurationFile().getParentDirectory().isEmpty())
            path = getConfigurationFile().getPrimarySection() + "." + path;

        yamlConfiguration.set(path, model);

        getConfigurationFile().saveFile();

        return this;
    }

    @Override
    protected void createFile() {
        if (getConfigurationFile().getParentDirectory().isEmpty()) super.createFile();

        File directory = FileUtil.createDirectory(MiniGame.getInstance().getDataFolder(), getConfigurationFile().getParentDirectory());

        File file = FileUtil.createFile(directory, getConfigurationFile());

        getConfigurationFile().setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
        getConfigurationFile().setFile(file);
    }
}
