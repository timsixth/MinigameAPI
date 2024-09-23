package pl.timsixth.minigameapi.api.file;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.util.FileUtil;

import java.io.File;

public class ManyYamlFileDao extends AbstractYamlFileDao {
    public ManyYamlFileDao(Model model) {
        super(model);
    }

    @Override
    public Object save(Model model) {
        createFile();

        return this.update(model);
    }

    @Override
    public boolean delete(Model model) {
        loadFiles();

        getConfigurationFile().setFile(null);
        getConfigurationFile().setYamlConfiguration(null);

        return FileUtil.deleteFile(getConfigurationFile());
    }

    @Override
    public Object update(Model model) {
        loadFiles();

        YamlConfiguration yamlConfiguration = getConfigurationFile().getYamlConfiguration();

        String path = getConfigurationFile().getIdSection().toString();

        if (!getConfigurationFile().getParentDirectory().isEmpty())
            path = getConfigurationFile().getPrimarySection() + "." + path;

        yamlConfiguration.set(path, model);

        getConfigurationFile().saveFile();

        return model;
    }

    @Override
    public void createFile() {
        if (getConfigurationFile().getParentDirectory().isEmpty()) super.createFile();

        File directory = FileUtil.createDirectory(MiniGame.getInstance().getDataFolder(), getConfigurationFile().getParentDirectory());

        File file = FileUtil.createFile(directory, getConfigurationFile());

        getConfigurationFile().setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
        getConfigurationFile().setFile(file);
    }

    private void loadFiles() {
        ConfigurationFile configurationFile = getConfigurationFile();
        if (configurationFile.getYamlConfiguration() != null || getConfigurationFile().getFile() != null) return;

        File directory = new File(MiniGame.getInstance().getDataFolder(), getConfigurationFile().getParentDirectory());
        File file = new File(directory, getConfigurationFile().getName());

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        getConfigurationFile().setYamlConfiguration(yamlConfiguration);
        getConfigurationFile().setFile(file);
    }
}
