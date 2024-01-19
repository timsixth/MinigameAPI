package pl.timsixth.exampleminigame.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.exampleminigame.ExampleMiniGamePlugin;

import java.io.File;

@Getter
public final class ConfigFile {

    @Getter(AccessLevel.NONE)
    private final ExampleMiniGamePlugin exampleMiniGamePlugin;

    private File messagesFile;

    private YamlConfiguration ymlMessages;


    public ConfigFile(ExampleMiniGamePlugin exampleMiniGamePlugin) {
        this.exampleMiniGamePlugin = exampleMiniGamePlugin;
        createFiles();
        loadFiles();
    }

    private void createFiles() {
        messagesFile = createFile("messages.yml");
    }

    private void loadFiles() {
        ymlMessages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private File createFile(String name) {
        if (!exampleMiniGamePlugin.getDataFolder().mkdir()) {
            exampleMiniGamePlugin.getDataFolder().mkdirs();
        }
        File file = new File(exampleMiniGamePlugin.getDataFolder(), name);
        if (!file.exists()) {
            exampleMiniGamePlugin.saveResource(name, true);
        }

        return file;
    }
}
