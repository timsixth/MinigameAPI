package pl.timsixth.minigameapi.api.addon.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.api.addon.model.Addon;
import pl.timsixth.minigameapi.api.addon.model.AddonImpl;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Getter
@RequiredArgsConstructor
public class AddonManagerImpl implements AddonManager {

    private final MiniGameApiPlugin miniGameApiPlugin;

    private final List<Addon> addons = new ArrayList<>();

    @Override
    public File download(String addonName, String version) throws IOException {
        URL downloadUrl = getDownloadUrl(addonName, version);

        if (downloadUrl == null) throw new IllegalStateException("Can not download because download link is null");

        String fileName = getFileName(downloadUrl);

        if (!fileName.endsWith(".jar")) throw new IllegalStateException("This file is not a JAR file.");

        InputStream in = downloadUrl.openStream();

        File targetFile = new File(miniGameApiPlugin.getAddonsFile(), fileName);

        Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return targetFile;
    }

    @Override
    public void update(Addon addon) {

    }

    @Override
    public Optional<Addon> getAddon(String name) {
        return addons.stream()
                .filter(addon -> addon.getName().equalsIgnoreCase(name))
                .findAny();
    }

    private URL getDownloadUrl(String repositoryName, String version) throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();
        GHRepository repository = gitHub.getRepository(repositoryName);
        if (version.equalsIgnoreCase("latest")) {
            GHRelease latestRelease = repository.getLatestRelease();
            if (latestRelease == null)
                throw new IllegalStateException("Can not download from this repository, release is not defined");
            return getUrl(latestRelease);
        } else {
            for (GHRelease ghRelease : repository.listReleases().toList()) {
                if (ghRelease.getTagName().equalsIgnoreCase(version)) {
                    return getUrl(ghRelease);
                }
            }
        }
        return null;
    }

    private URL getUrl(GHRelease latestRelease) throws IOException {
        GHAsset ghAsset = latestRelease.listAssets().toList().get(0);

        return new URL(ghAsset.getBrowserDownloadUrl());
    }

    private String getFileName(URL url) {
        String urlStr = url.toString();

        int latestIndex = urlStr.length();
        int latestIndexSlash = urlStr.lastIndexOf("/");

        return urlStr.substring(latestIndexSlash + 1, latestIndex);
    }

    @Override
    public void loadAddons(File addons) throws InvalidPluginException {
        File[] files = addons.listFiles();
        if (files == null) return;

        for (File file : files) {
            loadAddon(file);
        }
    }

    private Addon registerAddon(File file) {
        YamlConfiguration yamlConfiguration = loadAddonDescription(file);

        if (yamlConfiguration == null) throw new IllegalStateException("Can not get values from addon.yml");
        Addon addon = new AddonImpl(yamlConfiguration.getString("name"), yamlConfiguration.getString("displayName"));

        if (yamlConfiguration.contains("repository"))
            addon = new AddonImpl(yamlConfiguration.getString("name"),
                    yamlConfiguration.getString("repository"), yamlConfiguration.getString("displayName"));

        return addon;
    }

    private YamlConfiguration loadAddonDescription(File file) {
        YamlConfiguration data;
        try (JarFile jarFile = new JarFile(file)) {

            JarEntry entry = jarFile.getJarEntry("addon.yml");

            if (entry == null)
                throw new IllegalStateException("Addon with name: " + jarFile.getName() + " does not have addon.yml");

            BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)));
            data = new YamlConfiguration();

            data.load(reader);
            reader.close();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    @Override
    public void loadAddon(File file) throws InvalidPluginException {
        Addon addon = registerAddon(file);

        if (addons.contains(addon)) throw new IllegalStateException("Addon with name " + addon + " already loaded");

        addons.add(addon);

        Plugin plugin = miniGameApiPlugin.getPluginLoader().loadPlugin(file);
        miniGameApiPlugin.getServer().getPluginManager().enablePlugin(plugin);
    }
}
