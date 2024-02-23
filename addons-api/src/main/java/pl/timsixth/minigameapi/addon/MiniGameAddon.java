package pl.timsixth.minigameapi.addon;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.command.ParentCommand;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.api.loader.Loaders;

/**
 * Base class for every addon
 */
@Getter
public class MiniGameAddon extends JavaPlugin {


    private Loaders loaders;
    private ParentCommand adminCommand;
    private ParentCommand playerCommand;

    @Override
    public void onEnable() {
        MiniGame miniGame = MiniGame.getInstance();

        loaders = new Loaders(miniGame.getPluginConfiguration());

        adminCommand = miniGame.getAdminCommand();
        playerCommand = miniGame.getPlayerCommand();
    }

    /**
     * Adds sub command to admin command
     *
     * @param subCommand sub command to add
     */
    protected void addAdminSubCommand(SubCommand subCommand) {
        adminCommand.addSubCommand(subCommand);
    }

    /**
     * Adds sub command to player command
     *
     * @param subCommand sub command to add
     */
    protected void addPlayerSubCommand(SubCommand subCommand) {
        playerCommand.addSubCommand(subCommand);
    }
}
