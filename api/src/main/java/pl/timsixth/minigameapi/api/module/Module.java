package pl.timsixth.minigameapi.api.module;

/**
 * Represents external library module
 */
public interface Module {

    /**
     * Calls when module is enabling
     */
    default void onEnable() {
    }

    /**
     * Calls when module is disabling
     */
    default void onDisable() {
    }

    /**
     * @return module name
     */
    String getName();

    /**
     * Defines which other modules are necessary to load this module
     *
     * @return array of modules names
     */
    default String[] requiredModules() {
        return new String[]{};
    }
}
