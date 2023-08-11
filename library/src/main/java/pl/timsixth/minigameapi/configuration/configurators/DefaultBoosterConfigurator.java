package pl.timsixth.minigameapi.configuration.configurators;

import pl.timsixth.minigameapi.configuration.Configurator;
import pl.timsixth.minigameapi.configuration.type.BoosterConfiguration;
import pl.timsixth.minigameapi.util.ChatUtil;

import java.util.Arrays;
import java.util.List;

public class DefaultBoosterConfigurator implements Configurator<BoosterConfiguration> {

    private final List<String> adminBoosterHelp = Arrays.asList(
            "&7/&a<your_main_minigame_command> booster create <booster_name> &7- Opens GUI to creation boosters",
            "&7/&a<your_main_minigame_command> booster help &7- All booster commands",
            "&7/&a<your_main_minigame_command> booster give <booster_name> <global|player_name> &7- Gives booster to everyone or someone",
            "&7/&a<your_main_minigame_command> booster player <player_name> &7- Opens GUI with player's boosters"
    );

    @Override
    public BoosterConfiguration configure() {
        return BoosterConfiguration.builder()
                .adminBoosterHelp(ChatUtil.chatColor(adminBoosterHelp))
                .build();
    }
}
