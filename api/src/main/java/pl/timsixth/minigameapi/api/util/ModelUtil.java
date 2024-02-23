package pl.timsixth.minigameapi.api.util;

import lombok.experimental.UtilityClass;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.file.ManyFilesModel;
import pl.timsixth.minigameapi.api.file.SingleFileModel;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.model.annoations.IgnoreFields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@UtilityClass
public class ModelUtil {
    /**
     * Finds correct fields for {@link AbstractDbModel#init()} or {@link SingleFileModel#init()}  or {@link ManyFilesModel#init()}  method
     *
     * @param object base class to fiend fields
     * @return an array of correct fields or empty array when could not find fields
     */
    public static Field[] findFields(Object object) {
        Field[] fields = {};

        Class<?> clazz = object.getClass();

        while (fields.length == 0 || hasStaticFields(fields) || hasIgnoredFields(object.getClass())) {
            fields = clazz.getDeclaredFields();

            if (clazz.getSuperclass().equals(Object.class)) break;
            if (hasIdsFields(fields)) break;

            clazz = clazz.getSuperclass();
        }

        if (hasIgnoredFields(clazz) || hasStaticFields(fields)) fields = new Field[]{};

        return fields;
    }

    /**
     * Checks that fields array has static fields
     *
     * @param fields fields array to check
     * @return true if array has fields with static modifier, otherwise false
     */
    private static boolean hasStaticFields(Field[] fields) {
        return Arrays.stream(fields)
                .anyMatch(field -> Modifier.isStatic(field.getModifiers()));
    }

    /**
     * Checks that class has {@link IgnoreFields} annotation
     *
     * @param clazz class to check
     * @return true if class has {@link IgnoreFields}, otherwise false
     */
    private static boolean hasIgnoredFields(Class<?> clazz) {
        return clazz.isAnnotationPresent(IgnoreFields.class);
    }

    /**
     * Checks that fields array has fields with {@link Id} or {@link IdSection} annotation
     *
     * @param fields fields array to check
     * @return true if array has fields with Ids annotations, otherwise false
     */
    private static boolean hasIdsFields(Field[] fields) {
        return Arrays.stream(fields)
                .anyMatch(field -> field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(IdSection.class));
    }
}
