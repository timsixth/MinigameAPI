package pl.timsixth.thetag.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import pl.timsixth.guilibrary.core.util.ItemBuilder;
import pl.timsixth.minigameapi.api.util.ChatUtil;
import pl.timsixth.thetag.TheTagPlugin;
import pl.timsixth.thetag.util.PotionEffectUtil;

import java.util.List;
import java.util.Map;

@Getter
public final class Settings {

    @Getter(AccessLevel.NONE)
    private final TheTagPlugin theTagPlugin;

    private int maxPlayers;
    private int minPlayers;
    private int startTimer;
    private int roundTimer;
    private int finalTimer;
    private boolean usePotionEffectsInFinal;
    private Map<PotionEffectType, Integer> theTagEffects;
    private Map<PotionEffectType, Integer> noTheTagEffects;
    private double costOfWin;

    private List<String> scoreboardLines;
    private String scoreboardTitle;

    private String adminPermission;

    private Location lobbyLocation;

    private Material leaveItemMaterial;
    private String leaveItemName;

    public Settings(TheTagPlugin theTagPlugin) {
        this.theTagPlugin = theTagPlugin;
        loadSettings();
    }

    void loadSettings() {
        FileConfiguration config = theTagPlugin.getConfig();
        maxPlayers = config.getInt("game.max_players");
        minPlayers = config.getInt("game.min_players");
        startTimer = config.getInt("game.seconds_start");
        roundTimer = config.getInt("game.round_time");
        finalTimer = config.getInt("game.final_time");
        usePotionEffectsInFinal = config.getBoolean("game.usePotionEffectsInFinal");
        theTagEffects = PotionEffectUtil.getPotionEffects(config.getStringList("game.theTagEffects"));
        noTheTagEffects = PotionEffectUtil.getPotionEffects(config.getStringList("game.noTheTagEffects"));
        costOfWin = config.getDouble("game.costOfWin");

        adminPermission = config.getString("permission");

        scoreboardTitle = ChatUtil.chatColor(config.getString("scoreboard.title"));
        scoreboardLines = config.getStringList("scoreboard.lines");

        lobbyLocation = config.getObject("lobby.location", Location.class);

        leaveItemMaterial = Material.getMaterial(config.getString("leaveItem.material"));
        leaveItemName = ChatUtil.chatColor(config.getString("leaveItem.name"));

    }

    public ItemStack leaveItem() {
        return new ItemBuilder(leaveItemMaterial, 1)
                .setName(leaveItemName)
                .toItemStack();
    }
    public void setLobbyLocation(Location location) {
        lobbyLocation = location;

        FileConfiguration config = theTagPlugin.getConfig();

        config.set("lobby.location", location);

        theTagPlugin.saveConfig();
    }
}
