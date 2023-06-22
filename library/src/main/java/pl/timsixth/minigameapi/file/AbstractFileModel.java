package pl.timsixth.minigameapi.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import pl.timsixth.minigameapi.util.FileUtil;

import java.io.File;

/**
 * The class represents every model which is saving in file
 */
abstract class AbstractFileModel implements FileModel, ConfigurationSerializable {

    private final ConfigurationFile configurationFile = new ConfigurationFile();

    /**
     * The method saves model to file
     *
     * @return saved model
     */
    @Override
    public Object save() {
        createFile();

        return this;
    }

    /**
     * The method set YamlConfiguration and File
     */
    private void createFile() {
        File file = FileUtil.createFile(configurationFile);
        configurationFile.setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
        configurationFile.setFile(file);
    }

    /**
     * The method delete model from file
     *
     * @return true if model with section id exists otherwise false
     */
    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * The method update whole fields in file
     *
     * @return updated model
     */
    @Override
    public Object update() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * @return ConfigurationFile class which contains information about model
     */
    @Override
    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }

    public void init() {
        try {
            configurationFile.prepareModel(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }
}
