package pl.timsixth.minigameapi.api.module.mongodb;

import lombok.Getter;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.mongodb.core.configuration.DefaultMongoDbModuleConfigurator;
import pl.timsixth.minigameapi.api.module.mongodb.core.configuration.MongoDbModuleConfiguration;

@Getter
public class MongoDbModule implements Module {

    @Getter
    private static MongoDbModule instance;

    private final MongoDbModuleConfiguration mongoDbModuleConfiguration;
    private MongoDbConnector mongoDbConnector;

    public MongoDbModule() {
        this(new DefaultMongoDbModuleConfigurator().configure());
    }

    public MongoDbModule(MongoDbModuleConfiguration mongoDbModuleConfiguration) {
        instance = this;

        this.mongoDbModuleConfiguration = mongoDbModuleConfiguration;
    }

    @Override
    public String getName() {
        return "minigameapi-mongodb";
    }

    @Override
    public void onEnable() {
        mongoDbConnector = new MongoDbConnector(mongoDbModuleConfiguration);

        mongoDbConnector.connect();
    }

    @Override
    public void onDisable() {
        mongoDbConnector.disconnect();
    }
}
