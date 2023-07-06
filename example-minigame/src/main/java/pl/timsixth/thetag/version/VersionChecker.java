package pl.timsixth.thetag.version;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import pl.timsixth.thetag.util.HttpConnectionUtil;

import java.io.IOException;

public final class VersionChecker {

    private static final String API_URL = "https://timsixth.pl/api/plugins/%s/currentVersion";
    private String currentVersion;
    private final Plugin plugin;

    public VersionChecker(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), plugin);
    }

    public void checkVersion() {
        try {
            currentVersion = getNewestVersion();
        } catch (IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    private String getNewestVersion() throws IOException {
        JsonObject jsonObject = (JsonObject) HttpConnectionUtil.connect(String.format(API_URL, plugin.getDescription().getName()), "GET");
        JsonElement jsonElement = jsonObject.get("currentVersion");

        if (jsonElement == null) {
            return jsonObject.get("error").toString().replace("\"", "");
        }

        return jsonElement.toString().replace("\"", "");
    }

    private boolean isUpToDate() {
        return plugin.getDescription().getVersion().equalsIgnoreCase(currentVersion);
    }

    private static class PlayerJoinListener implements Listener {

        private final VersionChecker versionChecker;

        private PlayerJoinListener(VersionChecker versionChecker) {
            this.versionChecker = versionChecker;
        }

        @EventHandler
        private void onJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            if (!player.isOp()) return;
            if (versionChecker.currentVersion == null) {
                player.sendMessage(ChatColor.RED + "The version checker service is unavailable");
                return;
            }
            if (!versionChecker.isUpToDate()) {
                player.sendMessage(ChatColor.RED + "[" + versionChecker.plugin.getDescription().getName() + "] Please update plugin to newest version.");
                player.sendMessage(ChatColor.YELLOW + "Plugin version on your server: " + versionChecker.plugin.getDescription().getVersion());
                player.sendMessage(ChatColor.YELLOW + "Current plugin version: " + versionChecker.currentVersion);
            } else {
                player.sendMessage(ChatColor.GREEN + "[" + versionChecker.plugin.getDescription().getName() + "] is up to date");
            }
        }
    }
}
