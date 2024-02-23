package pl.timsixth.minigameapi.api.database;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.database.annoations.Table;
import pl.timsixth.minigameapi.api.model.InitializableModel;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.util.ModelUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Template method for {@link DbModel}
 * Every DbModel must extends {@link AbstractDbModel}
 * <p>
 * In subclass constructor you must call init() method,
 * because superclass must get values from fields and validate id.
 * This system is important to save, update and delete data.
 * </p>
 *
 * @see DbModel
 */
public abstract class AbstractDbModel implements DbModel, InitializableModel {

    private final Map<String, Object> data = new LinkedHashMap<>();
    private String idName;
    private Object idValue;
    private String tableName;

    @Override
    public Object save() {
        return save(this);
    }

    @Override
    public Object update() {
        return update(this);
    }

    @Override
    public boolean delete() {
        return delete(this);
    }

    /**
     * Gets correct value of id
     *
     * @return correct id value
     */
    protected final Object getId() {
        Object id = idValue;

        if (idValue instanceof String || idValue instanceof UUID) {
            id = "'" + idValue + "'";
        }
        return id;
    }

    /**
     * Executes async query
     *
     * @param query query to execute
     */
    public void executeUpdate(String query) {
        ISQLDataBase sqlDataBase = DatabasesApiPlugin.getApi().getCurrentSqlDataBase();

        try {
            sqlDataBase.getAsyncQuery().update(query);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves fields and field's values to map.
     * Please call this method after initialized every field.
     */
    @Override
    public void init() {
        init(this);
    }

    @Override
    public void init(Model object) {
        Class<?> clazz = object.getClass();

        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);

            tableName = table.name();
        }

        Field[] declaredFields = ModelUtil.findFields(object);

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            if (Modifier.isStatic(declaredField.getModifiers())) return;

            if (declaredField.isAnnotationPresent(Id.class)) {
                if (idValue != null)
                    throw new IllegalStateException("Every database model can has only one field annotated as Id");

                try {
                    idName = declaredField.getName();
                    idValue = declaredField.get(object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            setValue(object, declaredField);
        }
    }

    /**
     * Save value from subclass field to map
     *
     * @param declaredField field to get value
     */
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

    /**
     * Updates fields values in map after every action
     */
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

    @Override
    public Object save(Model model) {
        updateValues(model);

        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.insert(getTableNameWithPrefix(), null, data.values()).build();

        executeUpdate(query);

        return model;
    }

    @Override
    public boolean delete(Model model) {
        updateValues(model);

        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(getTableNameWithPrefix())
                .where(idName + " = " + getId().toString())
                .build();

        executeUpdate(query);

        return true;
    }

    @Override
    public Object update(Model model) {
        updateValues(model);

        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.update(getTableNameWithPrefix(), data)
                .where(idName + " = " + getId().toString())
                .build();

        executeUpdate(query);

        return model;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
