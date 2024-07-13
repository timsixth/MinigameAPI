package pl.timsixth.minigameapi.api.module.exception;

/**
 * This exception is thrown when module system does not work correctly
 */
public class ModuleException extends RuntimeException {

    public ModuleException(String moduleName, String requiredModule) {
        super("Can't load module " + moduleName + " because, " + requiredModule + " is required to work");
    }

    public ModuleException(String message) {
        super(message);
    }


}
