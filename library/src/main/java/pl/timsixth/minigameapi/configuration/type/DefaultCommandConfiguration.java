package pl.timsixth.minigameapi.configuration.type;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.configuration.Configuration;

/**
 * The class to configure your commands messages
 */
@Builder
@Getter
public class DefaultCommandConfiguration implements Configuration {

    private String doNotHavePermissionMessage;
    private String onlyPlayersMessage;
}
