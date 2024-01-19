package pl.timsixth.exampleminigame.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.timsixth.exampleminigame.ExampleMiniGamePlugin;
import pl.timsixth.minigameapi.api.util.ChatUtil;

@Getter
public final class Settings {

    @Getter(AccessLevel.NONE)
    private final ExampleMiniGamePlugin exampleMiniGamePlugin;

    private int maxPlayers;
    private int minPlayers;
    private int startTimer;
    private double costOfWin;

    private String adminPermission;

    private Location lobbyLocation;

    private Material leaveItemMaterial;
    private String leaveItemName;

    private int neededBlockToWin;

    public Settings(ExampleMiniGamePlugin exampleMiniGamePlugin) {
        this.exampleMiniGamePlugin = exampleMiniGamePlugin;
        loadSettings();
    }

    void loadSettings() {
        FileConfiguration config = exampleMiniGamePlugin.getConfig();
        maxPlayers = config.getInt("game.max_players");
        minPlayers = config.getInt("game.min_players");
        startTimer = config.getInt("game.seconds_start");
        costOfWin = config.getDouble("game.costOfWin");

        adminPermission = config.getString("permission");

        lobbyLocation = config.getObject("lobby.location", Location.class);

        leaveItemMaterial = Material.getMaterial(config.getString("leaveItem.material"));
        leaveItemName = ChatUtil.chatColor(config.getString("leaveItem.name"));

        neededBlockToWin = config.getInt("game.neededBlockToWin");
    }

    public ItemStack leaveItem() {
        ItemStack item = new ItemStack(leaveItemMaterial, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(leaveItemName);

        item.setItemMeta(meta);

        return item;
    }

    public void setLobbyLocation(Location location) {
        lobbyLocation = location;

        FileConfiguration config = exampleMiniGamePlugin.getConfig();

        config.set("lobby.location", location);

        exampleMiniGamePlugin.saveConfig();
    }
}
