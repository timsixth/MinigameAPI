package pl.timsixth.thetag.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.timsixth.minigameapi.api.util.ChatUtil;

import java.util.List;

@Getter
public final class Messages {

    @Getter(AccessLevel.NONE)
    private final ConfigFile configFile;

    private String noPermission;
    private String setLobbySpawn;
    private String isNotANumber;
    private String reloadedFiles;

    private List<String> playerHelp;
    private List<String> adminHelp;
    private List<String> playerStats;
    private String playerDoesNotExits;
    private String addedCoins;
    private String removedCoins;
    private String doesntHaveCoins;
    private String greaterThanZero;
    private String dontHaveCoins;

    private String arenaCreated;
    private String arenaExists;
    private String arenaDoesNotExists;
    private String setGameSpawn;
    private String arenaCurrentlyPlaying;
    private String arenaList;
    private String emptyArenaList;
    private String setGameLobbySpawn;
    private String leftPlayer;

    private String gameLeft;
    private String gameJoined;
    private String gameAlreadyJoined;
    private String playerWon;
    private String removedTheTag;
    private String gameStarted;
    private String removedTheTagToPlayer;
    private String notCurrentlyPlaying;
    private String startingCanceled;
    private String gameStarting;
    private String nextRoundStarted;
    private String dontHaveAnyEmptyArena;

    private String disabledCosmetic;
    private String enabledCosmetic;
    private String boughtCosmetic;
    private String resetAllCosmetics;
    private String resetAllCosmeticsCategory;
    private String pleaseResetCosmetic;

    private String menuDoesntExists;


    public Messages(ConfigFile configFile) {
        this.configFile = configFile;
        loadMessages();
    }

    void loadMessages() {
        YamlConfiguration yamlConfiguration = configFile.getYmlMessages();

        playerHelp = yamlConfiguration.getStringList("player.playerHelp");
        adminHelp = yamlConfiguration.getStringList("player.adminHelp");
        playerStats = yamlConfiguration.getStringList("player.playerStats");
        playerDoesNotExits = ChatUtil.chatColor(yamlConfiguration.getString("player.playerDoesNotExits"));
        addedCoins = ChatUtil.chatColor(yamlConfiguration.getString("player.addedCoins"));
        removedCoins = ChatUtil.chatColor(yamlConfiguration.getString("player.removedCoins"));
        doesntHaveCoins = ChatUtil.chatColor(yamlConfiguration.getString("player.doesntHaveCoins"));
        greaterThanZero = ChatUtil.chatColor(yamlConfiguration.getString("player.greaterThanZero"));
        dontHaveCoins = ChatUtil.chatColor(yamlConfiguration.getString("player.dontHaveCoins"));

        arenaCreated = ChatUtil.chatColor(yamlConfiguration.getString("arena.arenaCreated"));
        arenaExists = ChatUtil.chatColor(yamlConfiguration.getString("arena.arenaExists"));
        arenaDoesNotExists = ChatUtil.chatColor(yamlConfiguration.getString("arena.arenaDoesNotExists"));
        setGameSpawn = ChatUtil.chatColor(yamlConfiguration.getString("arena.setGameSpawn"));
        arenaCurrentlyPlaying = ChatUtil.chatColor(yamlConfiguration.getString("arena.arenaCurrentlyPlaying"));
        arenaList = ChatUtil.chatColor(yamlConfiguration.getString("arena.arenaList"));
        emptyArenaList = ChatUtil.chatColor(yamlConfiguration.getString("arena.emptyArenaList"));
        setGameLobbySpawn = ChatUtil.chatColor(yamlConfiguration.getString("arena.setGameLobbySpawn"));
        dontHaveAnyEmptyArena = ChatUtil.chatColor(yamlConfiguration.getString("arena.dontHaveAnyEmptyArena"));
        leftPlayer = ChatUtil.chatColor(yamlConfiguration.getString("arena.leftPlayer"));

        gameLeft = ChatUtil.chatColor(yamlConfiguration.getString("game.left"));
        gameJoined = ChatUtil.chatColor(yamlConfiguration.getString("game.joined"));
        gameAlreadyJoined = ChatUtil.chatColor(yamlConfiguration.getString("game.alreadyJoined"));
        playerWon = ChatUtil.chatColor(yamlConfiguration.getString("game.playerWon"));
        removedTheTag = ChatUtil.chatColor(yamlConfiguration.getString("game.removedTheTag"));
        gameStarted = ChatUtil.chatColor(yamlConfiguration.getString("game.gameStarted"));
        removedTheTagToPlayer = ChatUtil.chatColor(yamlConfiguration.getString("game.removedTheTagToPlayer"));
        notCurrentlyPlaying = ChatUtil.chatColor(yamlConfiguration.getString("game.notCurrentlyPlaying"));
        startingCanceled = ChatUtil.chatColor(yamlConfiguration.getString("game.startingCanceled"));
        gameStarting = ChatUtil.chatColor(yamlConfiguration.getString("game.gameStarting"));
        nextRoundStarted = ChatUtil.chatColor(yamlConfiguration.getString("game.nextRoundStarted"));

        noPermission = ChatUtil.chatColor(yamlConfiguration.getString("general.noPermission"));
        setLobbySpawn = ChatUtil.chatColor(yamlConfiguration.getString("general.setLobbySpawn"));
        isNotANumber = ChatUtil.chatColor(yamlConfiguration.getString("general.isNotANumber"));
        reloadedFiles = ChatUtil.chatColor(yamlConfiguration.getString("general.reloadedFiles"));

        disabledCosmetic = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.disabledCosmetic"));
        enabledCosmetic = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.enabledCosmetic"));
        boughtCosmetic = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.boughtCosmetic"));
        resetAllCosmetics = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.resetAllCosmetics"));
        resetAllCosmeticsCategory = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.resetAllCosmeticsCategory"));
        pleaseResetCosmetic = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.pleaseResetCosmetic"));

        menuDoesntExists = ChatUtil.chatColor(yamlConfiguration.getString("cosmetic.menuDoesntExists"));
    }
}
