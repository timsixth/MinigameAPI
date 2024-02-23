package pl.timsixth.minigameapi.api.file.impl;

import pl.timsixth.minigameapi.api.file.SingleFileModel;

import java.util.Map;
/**
 * Implementation of {@link SingleFileModel}
 * This class is necessary, because in adapter design pattern classes use composition instead of inheritance
 */
public class SingleFileModelImpl extends SingleFileModel {
    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
