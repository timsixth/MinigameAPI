package pl.timsixth.minigameapi.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.timsixth.minigameapi.MiniGameApiPlugin;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.util.List;

@Getter
public final class Messages {
    @Getter(AccessLevel.NONE)
    private MiniGameApiPlugin miniGameApiPlugin;

    private List<String> addonsHelp;
    private String addonEnabled;
    private String addonDisabled;
    private String installedAddons;
    private String addonAlreadyEnabled;
    private String addonAlreadyDisabled;
    private String addonDoesNotExists;
    private String addonDownloading;
    private String addonHasDownloaded;
    private String addonUpdated;
    private String invalidRepositoryName;

    public Messages(MiniGameApiPlugin miniGameApiPlugin) {
        this.miniGameApiPlugin = miniGameApiPlugin;

        loadMessages();
    }

    private void loadMessages() {
        FileConfiguration config = miniGameApiPlugin.getConfig();

        addonsHelp = ChatUtil.chatColor(config.getStringList("messages.addons_help"));
        addonEnabled = ChatUtil.chatColor(config.getString("messages.addon_enabled"));
        addonDisabled = ChatUtil.chatColor(config.getString("messages.addon_disabled"));
        installedAddons = ChatUtil.chatColor(config.getString("messages.installed_addons"));
        addonAlreadyEnabled = ChatUtil.chatColor(config.getString("messages.addon_already_enabled"));
        addonAlreadyDisabled = ChatUtil.chatColor(config.getString("messages.addon_already_disabled"));
        addonDoesNotExists = ChatUtil.chatColor(config.getString("messages.addon_does_not_exists"));
        addonDownloading = ChatUtil.chatColor(config.getString("messages.addon_downloading"));
        addonHasDownloaded = ChatUtil.chatColor(config.getString("messages.addon_has_downloaded"));
        addonUpdated = ChatUtil.chatColor(config.getString("messages.addon_updated"));
        invalidRepositoryName = ChatUtil.chatColor(config.getString("messages.invalid_repository_name"));
    }
}
