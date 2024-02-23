package pl.timsixth.exampleminigame.command.subcommand.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.UserCoins;
import pl.timsixth.minigameapi.api.coins.manager.UserCoinsManager;
import pl.timsixth.minigameapi.api.command.SubCommand;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.manager.UserCosmeticsManager;

import java.util.Optional;

/**
 * This is an example usage of cosmetics API
 */
@RequiredArgsConstructor
public class CosmeticsSubCommand implements SubCommand {

    private final UserCosmeticsManager userCosmeticsManager;
    private final CosmeticsManager cosmeticsManager;
    private final UserCoinsManager userCoinsManager;

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("reset_all")) {
                Optional<UserCosmetics> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());

                if (!userCosmeticsOptional.isPresent()) return true;

                UserCosmetics userCosmetics = userCosmeticsOptional.get();

                userCosmetics.resetAllCosmetics();
                player.sendMessage("You have restarted all categories");
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("buy")) {

                Optional<UserCoins> userCoinsOptional = userCoinsManager.getUserByUuid(player.getUniqueId());
                Optional<UserCosmetics> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());
                Optional<Cosmetic> cosmeticOptional = cosmeticsManager.getCosmeticByName(args[2]);

                if (!userCoinsOptional.isPresent()) return true;

                if (!cosmeticOptional.isPresent()) {
                    player.sendMessage("This cosmetic does not exist");
                    return true;
                }

                UserCosmetics userCosmetics = userCosmeticsOptional.orElseGet(() -> {
                    UserCosmetics user = MiniGame.getUserCosmeticsFactory().createUserCosmetics(player.getUniqueId());

                    userCosmeticsManager.addUser(user);

                    return user;
                });
                UserCoins userCoins = userCoinsOptional.get();
                Cosmetic cosmetic = cosmeticOptional.get();

                if (userCosmetics.hasCosmetic(cosmetic)) {
                    player.sendMessage("You already have this cosmetic");
                    return true;
                }

                if (!userCoins.hasCoins(10)) {
                    player.sendMessage("You don't have coins to but this cosmetic");
                    return true;
                }

                userCosmetics.addCosmetic(cosmetic);

                userCoins.removeCoins(10);

                player.sendMessage("You have bought new cosmetic");
            } else if (args[1].equalsIgnoreCase("toggle")) {
                Optional<UserCosmetics> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());
                Optional<Cosmetic> cosmeticOptional = cosmeticsManager.getCosmeticByName(args[2]);

                if (!userCosmeticsOptional.isPresent()) return true;

                if (!cosmeticOptional.isPresent()) {
                    player.sendMessage("This cosmetic does not exist");
                    return true;
                }

                Cosmetic cosmetic = cosmeticOptional.get();
                UserCosmetics userCosmetics = userCosmeticsOptional.get();

                if (userCosmetics.isCosmeticEnable(cosmetic)) {
                    userCosmetics.disableCosmetic(cosmetic);
                    player.sendMessage("Cosmetic has been disabled");
                } else {
                    userCosmetics.enableCosmetic(cosmetic);
                    player.sendMessage("Cosmetic has been enabled");
                }

            } else if (args[1].equalsIgnoreCase("reset_category")) {
                String category = args[2];

                Optional<UserCosmetics> userCosmeticsOptional = userCosmeticsManager.getUserByUuid(player.getUniqueId());

                if (!userCosmeticsOptional.isPresent()) return true;

                UserCosmetics userCosmeticsDbModel = userCosmeticsOptional.get();

                userCosmeticsDbModel.resetAllCosmeticsCategory(category);

                player.sendMessage("You have restarted all cosmetics from this category");
            }
        }

        return false;
    }

    @Override
    public String getName() {
        return "cosmetics";
    }
}
