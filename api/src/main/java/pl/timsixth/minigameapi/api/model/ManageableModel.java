package pl.timsixth.minigameapi.api.model;

import pl.timsixth.minigameapi.api.storage.Dao;

public interface ManageableModel extends Model, InjectableModel {

    /**
     * Gets class which has defined save, delete and upodate methods
     *
     * @return custom dao
     */
    Dao getDao();

    @Override
    default Object save(Model model) {
        return getDao().save(model);
    }

    @Override
    default boolean delete(Model model) {
        return getDao().delete(model);
    }

    @Override
    default Object update(Model model) {
        return getDao().update(model);
    }

    @Override
    default boolean delete() {
        return delete(this);
    }

    @Override
    default Object update() {
        return update(this);
    }

    @Override
    default Object save() {
        return save(this);
    }
}
