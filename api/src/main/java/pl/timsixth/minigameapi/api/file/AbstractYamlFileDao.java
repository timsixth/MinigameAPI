package pl.timsixth.minigameapi.api.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.storage.AbstractDao;
import pl.timsixth.minigameapi.api.util.FileUtil;

import java.io.File;

public abstract class AbstractYamlFileDao extends AbstractDao implements YamlFileDao {

    private final ConfigurationFile configurationFile = new ConfigurationFile();

    public AbstractYamlFileDao(Model model) {
        super(model);
        try {
            configurationFile.prepareModel(model);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    /**
     * The method set YamlConfiguration and File
     */
    protected void createFile() {
        File file = FileUtil.createFile(getConfigurationFile());
        getConfigurationFile().setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
        getConfigurationFile().setFile(file);
    }

    @Override
    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }

    @Override
    public Object save(Model model) {
        createFile();

        return model;
    }
}
