package pl.timsixth.minigameapi.api.module.mongodb.cosmetics;

import org.bson.Document;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.module.mongodb.core.dao.MongoDbDao;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbUserCosmeticsDao extends MongoDbDao {

    private final MongoDbUserCosmetics userCosmetics;

    public MongoDbUserCosmeticsDao(Model model) {
        super(model);

        this.userCosmetics = (MongoDbUserCosmetics) model;
    }

    @Override
    public Object save(Model model) {
        Document document = createDocument();

        getCollection().insertOne(document);

        return model;
    }

    private Document createDocument() {
        Document document = new Document();
        document.append("uuid", getId());

        List<Document> cosmeticsAsDocuments = new ArrayList<>();

        userCosmetics.getCosmetics().forEach(((cosmetic, status) -> {
            Document cosmeticAsDocument = new Document();
            cosmeticAsDocument.append("cosmetic_name", cosmetic.getName());
            cosmeticAsDocument.append("status", status);

            cosmeticsAsDocuments.add(cosmeticAsDocument);
        }));

        document.append("cosmetics", cosmeticsAsDocuments);

        return document;
    }

    @Override
    public Object update(Model model) {
        Document newDocument = createDocument();

        Document updateQuery = new Document();
        updateQuery.append("$set", newDocument);

        getCollection().updateOne(eq("uuid", getId()), updateQuery);

        return model;
    }
}
