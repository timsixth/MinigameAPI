package pl.timsixth.thetag.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.manager.intertaces.Reloadable;

import java.io.File;
import java.util.List;

@Getter
public final class ConfigFile {

    @Getter(AccessLevel.NONE)
    private final TheTagPlugin theTagPlugin;

    private File messagesFile;
    private File cosmeticsMainFile;
    private File cosmeticsWinFile;
    private File cosemticWalkFile;
    private File cosmeticsDefeatFile;
    private File cosmeticHitFile;

    private YamlConfiguration ymlMessages;
    private YamlConfiguration ymlCosmeticsMain;
    private YamlConfiguration ymlCosmeticsWalk;
    private YamlConfiguration ymlCosmeticsHit;
    private YamlConfiguration ymlCosmeticsDefeat;
    private YamlConfiguration ymlCosmeticsWin;

    public ConfigFile(TheTagPlugin theTagPlugin) {
        this.theTagPlugin = theTagPlugin;
        createFiles();
        loadFiles();
    }

    private void createFiles() {
        messagesFile = createFile("messages.yml");
        cosmeticsMainFile = createFile("cosmetics/main_gui.yml");
        cosmeticsWinFile = createFile("cosmetics/win_gui.yml");
        cosemticWalkFile = createFile("cosmetics/walk_gui.yml");
        cosmeticsDefeatFile = createFile("cosmetics/defeat_gui.yml");
        cosmeticHitFile = createFile("cosmetics/hit_gui.yml");

    }

    private void loadFiles() {
        ymlMessages = YamlConfiguration.loadConfiguration(messagesFile);
        ymlCosmeticsMain = YamlConfiguration.loadConfiguration(cosmeticsMainFile);
        ymlCosmeticsWalk = YamlConfiguration.loadConfiguration(cosemticWalkFile);
        ymlCosmeticsHit = YamlConfiguration.loadConfiguration(cosmeticHitFile);
        ymlCosmeticsDefeat = YamlConfiguration.loadConfiguration(cosmeticsDefeatFile);
        ymlCosmeticsWin = YamlConfiguration.loadConfiguration(cosmeticsWinFile);

    }

    private File createFile(String name) {
        if (!theTagPlugin.getDataFolder().mkdir()) {
            theTagPlugin.getDataFolder().mkdirs();
        }
        File file = new File(theTagPlugin.getDataFolder(), name);
        if (!file.exists()) {
            theTagPlugin.saveResource(name, true);
        }
        return file;
    }

    public void reloadFiles(List<Reloadable> reloadableList) {
        Bukkit.getScheduler().runTaskAsynchronously(theTagPlugin, theTagPlugin::reloadConfig);
        Bukkit.getScheduler().runTaskAsynchronously(theTagPlugin, () -> theTagPlugin.getSettings().loadSettings());
        Bukkit.getScheduler().runTaskAsynchronously(theTagPlugin, () -> theTagPlugin.getMessages().loadMessages());
        loadFiles();
        reloadableList.forEach(Reloadable::reload);
    }
}
