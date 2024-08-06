package pl.timsixth.minigameapi.api.module;

import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.module.exception.ModuleException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface ModuleManager {

    /**
     * Registers module
     *
     * @param module module to register
     */
    void registerModule(Module module);

    /**
     * Unregisters module
     *
     * @param module module to unregister
     */
    void unregisterModule(Module module);

    /**
     * Gets module by name
     *
     * @param name module name
     * @return optional of module
     */
    Optional<Module> getModule(String name);

    /**
     * @return list of modules
     */
    List<Module> getModules();

    /**
     * Enables modules
     */
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

    /**
     * Disables modules, and unregisters modules (Why method do two things? Method do two things because, when I want to remove module when for loop is working,
     * the remove method throws CurrentModificationException)
     */
    default void disableModules() {
        Iterator<Module> iterator = getModules().iterator();

        while (iterator.hasNext()) {
            Module module = iterator.next();

            module.onDisable();
            MiniGame.getInstance().getLogger().info("Module " + module.getName() + " disabled!");

            iterator.remove();
        }
    }
}
