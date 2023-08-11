package pl.timsixth.minigameapi.booster.command;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.command.SubCommand;
import pl.timsixth.minigameapi.configuration.type.BoosterConfiguration;

@RequiredArgsConstructor
public class BoosterSubCommand implements SubCommand {

    /*
     * /tta booster create <id>
     * /tta booster help
     * /tta booster give <booster_id> <global|player_name>
     * /tta booster player <player_name>
     */

    private final BoosterConfiguration boosterConfiguration;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("help")) {
                boosterConfiguration.getAdminBoosterHelp().forEach(player::sendMessage);
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("create")) {
                //TODO open anvil gui to write booster name
            }
        } else {
            boosterConfiguration.getAdminBoosterHelp().forEach(player::sendMessage);
        }
        return false;
    }

    @Override
    public String getName() {
        return "booster";
    }
}
