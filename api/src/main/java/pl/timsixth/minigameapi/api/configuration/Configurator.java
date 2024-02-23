package pl.timsixth.minigameapi.api.configuration;

/**
 * Represents every configurator class
 * @param <T> the configuration class
 */
public interface Configurator<T extends Configuration> {
    /**
     * @return the configuration class
     */
    T configure();
}
