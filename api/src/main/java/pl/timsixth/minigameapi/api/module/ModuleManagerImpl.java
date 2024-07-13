package pl.timsixth.minigameapi.api.module;

import lombok.Getter;
import pl.timsixth.minigameapi.api.module.exception.ModuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ModuleManager}
 */
@Getter
public class ModuleManagerImpl implements ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public void registerModule(Module module) {
        if (getModule(module.getName()).isPresent())
            throw new ModuleException("This " + module.getName() + " module is already registered");

        modules.add(module);
    }

    public void unregisterModule(Module module) {
        if (!getModule(module.getName()).isPresent())
            throw new ModuleException("This " + module.getName() + " module is not registered");

        modules.remove(module);
    }

    @Override
    public Optional<Module> getModule(String name) {
        return modules.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findAny();
    }
}
