package pl.timsixth.minigameapi.api.module.mongodb.core.configuration;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.api.configuration.Configuration;

@Getter
@Builder
public class MongoDbModuleConfiguration implements Configuration {

    private String mongoDbUri;
    private String databaseName;

}
