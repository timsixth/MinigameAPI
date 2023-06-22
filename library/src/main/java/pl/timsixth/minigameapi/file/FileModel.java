package pl.timsixth.minigameapi.file;

import pl.timsixth.minigameapi.model.Model;

/**
 * The class represents every FileModel
 */
public interface FileModel extends Model {
    /**
     *
     * @return information about file
     */
    ConfigurationFile getConfigurationFile();
}
