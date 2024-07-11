package pl.timsixth.minigameapi.api.file;

import pl.timsixth.minigameapi.api.model.ManageableModel;

/**
 * The class represents every FileModel
 */
public interface FileModel extends ManageableModel {
    /**
     * @return information about file
     */
    default ConfigurationFile getConfigurationFile() {
        return ((YamlFileDao) getDao()).getConfigurationFile();
    }
}
