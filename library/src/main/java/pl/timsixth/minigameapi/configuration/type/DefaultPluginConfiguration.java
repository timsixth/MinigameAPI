package pl.timsixth.minigameapi.configuration.type;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.configuration.Configuration;

/**
 * The class to configure your plugin settings
 */
@Builder
@Getter
public class DefaultPluginConfiguration implements Configuration {

    private boolean useDataBase;
    private String tablesPrefix;
}
