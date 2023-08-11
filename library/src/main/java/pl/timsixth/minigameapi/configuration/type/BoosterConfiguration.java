package pl.timsixth.minigameapi.configuration.type;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.configuration.Configuration;

import java.util.List;

@Builder
@Getter
public class BoosterConfiguration implements Configuration {

    private List<String> adminBoosterHelp;
}
