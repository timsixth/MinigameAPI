package pl.timsixth.thetag.manager;

import pl.timsixth.guilibrary.core.manager.YAMLMenuManager;
import pl.timsixth.guilibrary.core.manager.registration.ActionRegistration;
import pl.timsixth.thetag.config.ConfigFile;
import pl.timsixth.thetag.manager.intertaces.Reloadable;

public class MenuManager extends YAMLMenuManager implements Reloadable {

    private final ConfigFile configFile;

    public MenuManager(ActionRegistration actionRegistration, ConfigFile configFile) {
        super(actionRegistration);
        this.configFile = configFile;
    }

    @Override
    public void load() {
        load(configFile.getYmlCosmeticsMain());
        load(configFile.getYmlCosmeticsHit());
        load(configFile.getYmlCosmeticsWin());
        load(configFile.getYmlCosmeticsWalk());
        load(configFile.getYmlCosmeticsDefeat());
    }

    @Override
    public void reload() {
        getMenus().clear();
        load();
    }
}
