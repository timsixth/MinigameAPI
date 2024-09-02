package pl.timsixth.minigameapi.api.module.mongodb.core.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import lombok.AccessLevel;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.model.annotations.Id;
import pl.timsixth.minigameapi.api.module.mongodb.MongoDbModule;
import pl.timsixth.minigameapi.api.module.mongodb.core.MongoDbModel;
import pl.timsixth.minigameapi.api.storage.AbstractDao;
import pl.timsixth.minigameapi.api.util.ModelUtil;
import pl.timsixth.minigameapi.api.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbDao extends AbstractDao {

    private final Map<String, Object> data = new LinkedHashMap<>();
    private Pair<String, Object> id;

    private final MongoDbModel mongoDbModel;
    private final MongoDatabase mongoDatabase;
    @Getter(AccessLevel.PROTECTED)
    private final MongoCollection<Document> collection;

    public MongoDbDao(Model model) {
        super(model);

        init(model);

        mongoDbModel = (MongoDbModel) model;
        mongoDatabase = MongoDbModule.getInstance().getMongoDbConnector().getMongoDatabase();

        collection = getCollectionOrCreate(mongoDbModel.getCollectionNameWithPrefix());
    }

    private MongoCollection<Document> getCollectionOrCreate(String collectionName) {
        if (!collectionExists(collectionName)) {
            mongoDatabase.createCollection(collectionName);
        }

        return mongoDatabase.getCollection(collectionName);
    }

    private boolean collectionExists(String collectionName) {
        for (String name : mongoDatabase.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected MongoDbModel getModel() {
        return mongoDbModel;
    }

    private void init(Model model) {
        Field[] declaredFields = ModelUtil.findFields(model);

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            if (Modifier.isStatic(declaredField.getModifiers())) return;

            if (declaredField.isAnnotationPresent(Id.class)) {
                if (id != null)
                    throw new IllegalStateException("Every database model can has only one field annotated as Id");

                try {
                    id = new Pair<>(declaredField.getName(), declaredField.get(model));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            setValue(model, declaredField);
        }
    }

    private void setValue(Object object, Field declaredField) {
        try {
            Object value = declaredField.get(object);

            if (value instanceof UUID) {
                data.put(declaredField.getName(), value.toString());
            } else {
                data.put(declaredField.getName(), value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateValues(Object object) {
        Field[] declaredFields = ModelUtil.findFields(object);

        for (Map.Entry<String, Object> fieldNameAndValue : data.entrySet()) {

            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);

                if (Modifier.isStatic(declaredField.getModifiers())) return;

                if (fieldNameAndValue.getKey().equalsIgnoreCase(declaredField.getName())) continue;

                setValue(object, declaredField);
            }
        }
    }

    /**
     * Gets correct value of id
     *
     * @return correct id value
     */
    protected final Object getId() {
        Object id = this.id.getSecond();

        if (id instanceof String || id instanceof UUID) {
            id = id.toString();
        }

        return id;
    }

    @Override
    public Object save(Model model) {
        updateValues(model);

        Document document = createDocument();

        collection.insertOne(document);

        return model;
    }

    @Override
    public boolean delete(Model model) {
        updateValues(model);

        Bson query = eq(id.getFirst(), getId());

        DeleteResult deleteResult = collection.deleteOne(query);

        return deleteResult.getDeletedCount() == 1;
    }

    @Override
    public Object update(Model model) {
        updateValues(model);

        Document query = new Document();
        query.append(id.getFirst(), getId());

        Document newDocument = createDocument();

        Document updateQuery = new Document();
        updateQuery.append("$set", newDocument);

        collection.updateOne(query, updateQuery);

        return model;
    }

    private Document createDocument() {
        Document document = new Document();
        data.forEach(document::append);

        return document;
    }
}
