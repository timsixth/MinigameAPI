package pl.timsixth.minigameapi.api.module.exception;

public class ModuleException extends RuntimeException {

    public ModuleException(String moduleName, String requiredModule) {
        super("Can't load module " + moduleName + " because, " + requiredModule + " is required to work");
    }

    public ModuleException(String message) {
        super(message);
    }


}
