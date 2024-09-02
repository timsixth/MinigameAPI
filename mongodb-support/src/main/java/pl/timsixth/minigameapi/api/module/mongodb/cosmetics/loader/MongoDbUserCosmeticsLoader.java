package pl.timsixth.minigameapi.api.module.mongodb.cosmetics.loader;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.cosmetics.user.UserCosmetics;
import pl.timsixth.minigameapi.api.cosmetics.user.loader.UserCosmeticsLoader;
import pl.timsixth.minigameapi.api.module.mongodb.core.loader.AbstractMongoDbLoader;
import pl.timsixth.minigameapi.api.module.mongodb.cosmetics.MongoDbUserCosmetics;

import java.util.*;

@RequiredArgsConstructor
public class MongoDbUserCosmeticsLoader extends AbstractMongoDbLoader<UserCosmetics> implements UserCosmeticsLoader {

    private final CosmeticsManager cosmeticsManager;

    @Override
    protected String getCollectionName() {
        return MongoDbUserCosmetics.COLLECTION_NAME;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void loadDocument(Document document) {
        UserCosmetics userCosmetics = MiniGame.getUserCosmeticsFactory()
                .createUserCosmetics(UUID.fromString(document.getString("uuid")));

        Map<Cosmetic, Boolean> cosmetics = new HashMap<>();

        List<Document> cosmeticsAsDocuments = (List<Document>) document.get("cosmetics");

        for (Document cosmeticAsDocument : cosmeticsAsDocuments) {

            Optional<Cosmetic> cosmeticOptional = cosmeticsManager.getCosmeticByName(cosmeticAsDocument.getString("cosmetic_name"));

            if (!cosmeticOptional.isPresent()) continue;

            cosmetics.put(
                    cosmeticOptional.get(),
                    cosmeticAsDocument.getBoolean("status")
            );
        }

        userCosmetics.setCosmetics(cosmetics);

        addObject(userCosmetics);
    }
}
