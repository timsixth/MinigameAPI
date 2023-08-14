package pl.timsixth.minigameapi.configuration.type;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.configuration.Configuration;

/**
 * The class to configure your game settings
 */
@Builder
@Getter
public class GameConfiguration implements Configuration {

    private boolean useTeams;
    private boolean blockBreaking;
    private boolean blocksPlacing;
    private boolean droppingItems;
}
