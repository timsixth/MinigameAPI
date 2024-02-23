package pl.timsixth.minigameapi.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This class is a model adapter.
 * The adapter design pattern is necessary, because some models have the same implementation, but other annotations.
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractModelAdapter implements Model {

    protected final InitializableModel model;

    @Override
    public boolean delete() {
        return model.delete(this);
    }

    @Override
    public Object save() {
        return model.save(this);
    }

    @Override
    public Object update() {
        return model.update(this);
    }
}
