package pl.timsixth.minigameapi.api.addon.manager;

import com.rylinaux.plugman.PlugMan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
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

/**
 * Implementation of {@link AddonManager}
 *
 * @deprecated Addons system will be removed
 */
@Getter
@RequiredArgsConstructor
@Deprecated
public class AddonManagerImpl implements AddonManager {

    private final Plugin plugin;
    private final File addonsFile;

    private final List<Addon> addons = new ArrayList<>();

    @Override
    public File download(String addonName, String version) throws IOException {
        URL downloadUrl = getDownloadUrl(addonName, version);

        if (downloadUrl == null) throw new IllegalStateException("Can not download because download link is null");

        String fileName = getFileName(downloadUrl);

        if (!fileName.endsWith(".jar")) throw new IllegalStateException("This file is not a JAR file.");

        InputStream in = downloadUrl.openStream();

        File targetFile = new File(addonsFile, fileName);

        Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return targetFile;
    }

    @Override
    public void update(Addon addon) throws IOException, InvalidPluginException {
        if (Bukkit.getPluginManager().getPlugin("PlugManX") == null) return;

        if (addon.getRepository() == null || addon.getRepository().isEmpty()) return;

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        Plugin plugin = addon.toPlugin();

        addon.getPluginFile().delete();

        PlugMan.getInstance().getPluginUtil().unload(plugin);
        pluginManager.disablePlugin(plugin);

        File file = download(addon.getRepository(), "latest");

        addons.remove(addon);

        loadAddon(file);
    }

    @Override
    public Optional<Addon> getAddon(String name) {
        return addons.stream()
                .filter(addon -> addon.getName().equalsIgnoreCase(name))
                .findAny();
    }

    /**
     * Gets link to download addon from GitHub release
     *
     * @param repositoryName GitHub repository
     * @param version        release tag name
     * @return URL to download file or null
     * @throws IOException when can not parse link
     */
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

    /**
     * Gets link to download first asset from GitHub release
     *
     * @param release release to get link to download asset
     * @return URL to download file
     * @throws IOException when can not parse link
     */
    private URL getUrl(GHRelease release) throws IOException {
        GHAsset ghAsset = release.listAssets().toList().get(0);

        return new URL(ghAsset.getBrowserDownloadUrl());
    }

    /**
     * Gets file name from link to download asset
     *
     * @param url url to get file name
     * @return file name
     */
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

    /**
     * Creates new addon
     *
     * @param file JAR file with addon.yml
     * @return addon to register
     */
    private Addon createAddon(File file) {
        YamlConfiguration yamlConfiguration = loadAddonDescription(file);

        if (yamlConfiguration == null) throw new IllegalStateException("Can not get values from addon.yml");

        if (!yamlConfiguration.contains("name")) throw new IllegalStateException("Missing 'name' in addon.yml");

        if (!yamlConfiguration.contains("displayName"))
            throw new IllegalStateException("Missing 'displayName' in addon.yml");

        Addon addon = new AddonImpl(yamlConfiguration.getString("name"), yamlConfiguration.getString("displayName"), file);

        if (yamlConfiguration.contains("repository"))
            addon = new AddonImpl(yamlConfiguration.getString("name"), yamlConfiguration.getString("displayName")
                    , file, yamlConfiguration.getString("repository"));

        return addon;
    }

    /**
     * Gets addon description from addon.yml
     *
     * @param file JAR file with addon.yml
     * @return yaml configuration with addon description
     */
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
        Addon addon = createAddon(file);

        if (addons.contains(addon)) throw new IllegalStateException("Addon with name " + addon + " already loaded");

        addons.add(addon);

        Plugin plugin = this.plugin.getPluginLoader().loadPlugin(file);
        Bukkit.getPluginManager().enablePlugin(plugin);
    }
}
