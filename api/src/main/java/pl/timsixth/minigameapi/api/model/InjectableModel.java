package pl.timsixth.minigameapi.api.model;

public interface InjectableModel {

    /**
     * Saves new model to database or file
     *
     * @param model model to save
     * @return saved model
     */
    Object save(Model model);

    /**
     * Deletes model from file or table
     *
     * @param model model to delete
     * @return true operation is succeeded otherwise false
     */
    boolean delete(Model model);

    /**
     * Updates new model to database or file
     *
     * @param model model to update
     * @return updated model
     */
    Object update(Model model);
}
