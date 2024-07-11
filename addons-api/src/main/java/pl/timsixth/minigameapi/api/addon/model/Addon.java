package pl.timsixth.minigameapi.api.addon.model;

import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Represents registered addon
 *
 * @deprecated Addons system will be removed
 */
@Deprecated
public interface Addon {
    /**
     * Gets addon name, plugin name must be the same
     *
     * @return addon name
     */
    String getName();

    /**
     * Gets addon version
     *
     * @return addon version
     */
    String getVersion();

    /**
     * Gets repository name
     *
     * @return repository name
     */
    String getRepository();

    /**
     * Gets addon display name
     *
     * @return addon display name
     */
    String getDisplayName();

    /**
     * Gets addon JAR file in addons directory
     *
     * @return addon JAR file
     */
    File getPluginFile();

    /**
     * Converts addon to bukkit plugin
     *
     * @return addon converted to plugin
     */
    Plugin toPlugin();
}
