package pl.timsixth.minigameapi.api.util.options;

public interface CustomOptions {

    /**
     * Gets custom options
     *
     * @return custom options
     */
    Options options();

    /**
     * Gets custom options or creates when options are null
     *
     * @return custom options
     */
    Options getOptionsOrCreate();
}
