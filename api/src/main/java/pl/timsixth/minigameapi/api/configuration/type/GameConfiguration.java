package pl.timsixth.minigameapi.api.configuration.type;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.api.configuration.Configuration;

/**
 * The class to configure your game settings
 */
@Builder
@Getter
public class GameConfiguration implements Configuration {

    private boolean blockBreaking;
    private boolean blocksPlacing;
    private boolean droppingItems;
}
