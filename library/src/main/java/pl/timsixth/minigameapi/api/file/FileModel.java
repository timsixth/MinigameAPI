package pl.timsixth.minigameapi.api.file;

import pl.timsixth.minigameapi.api.model.Model;

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
