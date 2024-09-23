package pl.timsixth.minigameapi.api.module.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pl.timsixth.minigameapi.api.module.mongodb.core.configuration.MongoDbModuleConfiguration;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@RequiredArgsConstructor
@Getter
public final class MongoDbConnector {

    @Getter(AccessLevel.NONE)
    private final MongoDbModuleConfiguration mongoDbModuleConfiguration;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    void connect() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(new ConnectionString(mongoDbModuleConfiguration.getMongoDbUri()))
                .build();

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .build();

        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase(mongoDbModuleConfiguration.getDatabaseName())
                .withCodecRegistry(pojoCodecRegistry);
    }

    public void disconnect() {
        mongoClient.close();
    }
}
