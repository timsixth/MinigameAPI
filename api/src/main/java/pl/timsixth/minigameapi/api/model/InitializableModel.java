package pl.timsixth.minigameapi.api.model;

/**
 * Represents a initializable model
 *
 * @deprecated there is new models system
 */
@Deprecated
public interface InitializableModel extends Model, InjectableModel {
    /**
     * Initialize model fields which are unnecessary to save, update and delete methods
     */
    void init();

    /**
     * Initialize model fields which are unnecessary to save, update and delete methods
     *
     * @param model model to init
     */
    void init(Model model);
}
