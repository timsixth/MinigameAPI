package pl.timsixth.minigameapi.api.module.sql.core.dao;

import lombok.AccessLevel;
import lombok.Getter;
import pl.timsixth.minigameapi.api.model.Model;
import pl.timsixth.minigameapi.api.model.annotations.Id;
import pl.timsixth.minigameapi.api.module.sql.SQLModule;
import pl.timsixth.minigameapi.api.module.sql.core.DbModel;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.storage.AbstractDao;
import pl.timsixth.minigameapi.api.util.ModelUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The new way how to manage database models
 */
public class SQLDatabaseDao extends AbstractDao {

    private final Map<String, Object> data = new LinkedHashMap<>();
    private String idName;
    private Object idValue;

    @Getter(AccessLevel.PROTECTED)
    private final SQLDatabaseAdapter sqlDatabaseAdapter;
    private final DbModel dbModel;

    public SQLDatabaseDao(Model model) {
        super(model);

        init(model);

        dbModel = (DbModel) model;
        sqlDatabaseAdapter = SQLModule.getInstance().getSqlModuleConfiguration().getSqlDatabaseAdapter();
    }

    protected DbModel getModel() {
        return dbModel;
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
     * Initializes model data - read values from fields and set Id type
     *
     * @param model model which will be initialized
     */
    private void init(Model model) {
        Field[] declaredFields = ModelUtil.findFields(model);

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            if (Modifier.isStatic(declaredField.getModifiers())) return;

            if (declaredField.isAnnotationPresent(Id.class)) {
                if (idValue != null)
                    throw new IllegalStateException("Every database model can has only one field annotated as Id");

                try {
                    idName = declaredField.getName();
                    idValue = declaredField.get(model);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            setValue(model, declaredField);
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

        sqlDatabaseAdapter.insert(dbModel.getTableNameWithPrefix(), null, data.values());

        return model;
    }

    @Override
    public boolean delete(Model model) {
        updateValues(model);

        sqlDatabaseAdapter.deleteAllWhere(dbModel.getTableNameWithPrefix(), idName + " = " + getId().toString());

        return true;
    }

    @Override
    public Object update(Model model) {
        updateValues(model);

        sqlDatabaseAdapter.updateWhere(dbModel.getTableNameWithPrefix(), data, idName + " = " + getId().toString());

        return model;
    }
}
