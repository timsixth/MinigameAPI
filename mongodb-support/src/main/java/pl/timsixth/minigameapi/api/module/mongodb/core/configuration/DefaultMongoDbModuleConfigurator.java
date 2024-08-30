package pl.timsixth.minigameapi.api.module.mongodb.core.configuration;

import pl.timsixth.minigameapi.api.configuration.Configurator;

public class DefaultMongoDbModuleConfigurator implements Configurator<MongoDbModuleConfiguration> {
    @Override
    public MongoDbModuleConfiguration configure() {
        return MongoDbModuleConfiguration.builder()
                .mongoDbUri("mongodb://localhost:27017")
                .databaseName("minigameapi")
                .build();
    }
}
