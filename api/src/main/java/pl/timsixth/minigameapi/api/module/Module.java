package pl.timsixth.minigameapi.api.module;

public interface Module {

    default void onEnable() {
    }

    default void onDisable() {
    }

    String getName();

    default String[] requiredModules() {
        return new String[]{};
    }
}
