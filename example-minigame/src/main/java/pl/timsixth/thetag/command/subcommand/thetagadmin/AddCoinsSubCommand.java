package pl.timsixth.thetag.command.subcommand.thetagadmin;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.thetag.config.Messages;
import pl.timsixth.thetag.util.NumberUtil;
import pl.timsixth.thetag.util.PlayerUtil;

import java.util.Optional;
@RequiredArgsConstructor
public class AddCoinsSubCommand implements SubCommand {

    private final UserCoinsManager userCoinsManager;
    private final Messages messages;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 3) {
            Player player = (Player) sender;
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

            Optional<UserCoins> userCoinsOptional = userCoinsManager.getUserByUuid(offlinePlayer.getUniqueId());
            if (!userCoinsOptional.isPresent()) {
                PlayerUtil.sendMessage(player, messages.getPlayerDoesNotExits());
                return true;
            }

            if (!NumberUtil.isDouble(args[2])) {
                PlayerUtil.sendMessage(player, messages.getIsNotANumber());
                return true;
            }

            double coins = Double.parseDouble(args[2]);

            if (coins <= 0) {
                PlayerUtil.sendMessage(player, messages.getGreaterThanZero());
                return true;
            }

            UserCoins userCoinsDbModel = userCoinsOptional.get();

            userCoinsDbModel.addCoins(coins);
            PlayerUtil.sendMessage(player, messages.getAddedCoins().replace("{PLAYER_NAME}", player.getName()));
        }

        return false;
    }

    @Override
    public String getName() {
        return "addcoins";
    }
}
