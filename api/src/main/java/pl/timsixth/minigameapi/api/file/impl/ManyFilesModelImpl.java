package pl.timsixth.minigameapi.api.file.impl;

import pl.timsixth.minigameapi.api.file.ManyFilesModel;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.Map;

/**
 * Implementation of {@link ManyFilesModel}
 * This class is necessary, because in adapter design pattern classes use composition instead of inheritance
 */
@Deprecated
public class ManyFilesModelImpl extends ManyFilesModel {
    @Override
    public Map<String, Object> serialize() {
        return null;
    }

    @Override
    public Dao getDao() {
        return null;
    }
}
