package pl.timsixth.minigameapi.api.addon.manager;

import org.bukkit.plugin.InvalidPluginException;
import pl.timsixth.minigameapi.api.addon.model.Addon;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AddonManager {
    /**
     * Downloads new addon from GitHub repository
     *
     * @param addonName GitHub repository, e.g. (username_from_github)/(repo_name) - timsixth/BoostersMiniGameAddon
     * @param version   version of addon, the GitHub release tag, not pre-release, type latest to get latest release
     * @return downloaded file
     * @throws IOException when can not download file
     */
    File download(String addonName, String version) throws IOException;

    /**
     * Updates addon, gets the latest version
     *
     * @param addon addon to update
     * @throws IOException            when can not download file
     * @throws InvalidPluginException when can not load plugin
     */
    void update(Addon addon) throws IOException, InvalidPluginException;

    /**
     * Gets list of registered addons
     *
     * @return list of addons
     */
    List<Addon> getAddons();

    /**
     * Gets addon by plugin name
     *
     * @param name plugin name, must be unique
     * @return optional of addon
     */
    Optional<Addon> getAddon(String name);

    /**
     * Loads all addons
     *
     * @param addons addons directory
     * @throws InvalidPluginException when can not load plugin
     */
    void loadAddons(File addons) throws InvalidPluginException;

    /**
     * Loads single addon
     *
     * @param file addon file
     * @throws InvalidPluginException when can not load plugin
     */
    void loadAddon(File file) throws InvalidPluginException;
}
