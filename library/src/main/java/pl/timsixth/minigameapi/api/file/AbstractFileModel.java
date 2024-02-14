package pl.timsixth.minigameapi.api.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import pl.timsixth.minigameapi.api.model.InitializableModel;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.util.FileUtil;

import java.io.File;

/**
 * The class represents every model which is saving in file
 */
abstract class AbstractFileModel implements FileModel, ConfigurationSerializable, InitializableModel {

    private ConfigurationFile configurationFile;

    /**
     * The method saves model to file
     *
     * @return saved model
     */
    @Override
    public Object save() {
        return save(this);
    }

    /**
     * The method set YamlConfiguration and File
     */
    protected void createFile() {
        File file = FileUtil.createFile(getConfigurationFile());
       getConfigurationFile().setYamlConfiguration(YamlConfiguration.loadConfiguration(file));
        getConfigurationFile().setFile(file);
    }

    /**
     * The method delete model from file
     *
     * @return true if model with section id exists otherwise false
     */
    @Override
    public boolean delete() {
        return delete(this);
    }

    /**
     * The method update whole fields in file
     *
     * @return updated model
     */
    @Override
    public Object update() {
        return update(this);
    }

    /**
     * @return ConfigurationFile class which contains information about model
     */
    @Override
    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }

    @Override
    public void init() {
        init(this);
    }

    @Override
    public void init(Model object) {
        configurationFile = new ConfigurationFile();

        try {
            configurationFile.prepareModel(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    @Override
    public Object save(Model model) {
        createFile();

        return model;
    }

    @Override
    public boolean delete(Model model) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Object update(Model model) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
