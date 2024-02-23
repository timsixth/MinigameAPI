package pl.timsixth.minigameapi.api.configuration.type;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.api.configuration.Configuration;

/**
 * The class to configure your commands messages
 */
@Builder
@Getter
public class CommandConfiguration implements Configuration {

    private String doNotHavePermissionMessage;
    private String onlyPlayersMessage;
}
