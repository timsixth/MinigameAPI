package pl.timsixth.minigameapi.database;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.ISQLDataBase;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.database.annoations.Id;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Template method for {@link DbModel}
 * Every DbModel must extends {@link AbstractDbModel}
 * <p>
 * In subclass constructor you must call init() method, <br/>
 * because superclass must get values from fields and validate id.
 * This system is important to save, update and delete data.
 *
 * @see DbModel
 */
public abstract class AbstractDbModel implements DbModel {

    private final Map<String, Object> data = new LinkedHashMap<>();
    private String idName;
    private Object idValue;

    @Override
    public Object save() {
        updateValues();

        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.insert(getTableName(), null, data.values()).build();

        executeUpdate(query);

        return this;
    }

    @Override
    public Object update() {
        updateValues();

        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.update(getTableName(), data)
                .where(idName + " = " + getId().toString())
                .build();

        executeUpdate(query);

        return this;
    }

    @Override
    public boolean delete() {
        updateValues();

        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.deleteAll(getTableName())
                .where(idName + " = " + getId().toString())
                .build();

        executeUpdate(query);

        return true;
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
    protected void executeUpdate(String query) {
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
    protected void init() {
        Class<? extends AbstractDbModel> aClass = this.getClass();

        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            if (declaredField.isAnnotationPresent(Id.class)) {
                if (idValue != null)
                    throw new IllegalStateException("Every database model can has only one field annotated as Id");

                try {
                    idName = declaredField.getName();
                    idValue = declaredField.get(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            setValue(declaredField);
        }
    }

    /**
     * Save value from subclass field to map
     *
     * @param declaredField field to get value
     */
    private void setValue(Field declaredField) {
        try {
            Object value = declaredField.get(this);

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
    private void updateValues() {
        Class<? extends AbstractDbModel> aClass = this.getClass();

        Field[] declaredFields = aClass.getDeclaredFields();

        for (Map.Entry<String, Object> fieldNameAndValue : data.entrySet()) {

            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);

                if (fieldNameAndValue.getKey().equalsIgnoreCase(declaredField.getName())) continue;

                setValue(declaredField);
            }
        }
    }
}
