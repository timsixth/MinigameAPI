package pl.timsixth.minigameapi.api.module;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.module.exception.ModuleException;

import java.util.List;
import java.util.Optional;

public interface ModuleManager {

    void registerModule(Module module);

    void unregisterModule(Module module);

    Optional<Module> getModule(String name);

    List<Module> getModules();

    default void unregisterAllModules() {
        getModules().forEach(this::unregisterModule);
    }

    default void enableModules() {
        getModules().forEach(module -> {
            String[] requiredModules = module.requiredModules();

            for (String requiredModule : requiredModules) {
                Optional<Module> moduleOptional = getModule(requiredModule);
                if (!moduleOptional.isPresent()) {
                    throw new ModuleException(module.getName(), requiredModule);
                }
            }

            module.onEnable();
            MiniGame.getInstance().getLogger().info("Module " + module.getName() + " enabled!");
        });
    }

    default void disableModules() {
        getModules().forEach(module -> {
            module.onDisable();
            MiniGame.getInstance().getLogger().info("Module " + module.getName() + " disabled!");
        });
    }
}
