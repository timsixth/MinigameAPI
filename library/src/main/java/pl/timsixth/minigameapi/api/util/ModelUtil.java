package pl.timsixth.minigameapi.api.util;

import lombok.experimental.UtilityClass;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.model.annoations.IgnoreFields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@UtilityClass
public class ModelUtil {

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

    private static boolean hasStaticFields(Field[] fields) {
        return Arrays.stream(fields)
                .anyMatch(field -> Modifier.isStatic(field.getModifiers()));
    }

    private static boolean hasIgnoredFields(Class<?> clazz) {
        return clazz.isAnnotationPresent(IgnoreFields.class);
    }

    private static boolean hasIdsFields(Field[] fields) {
        return Arrays.stream(fields)
                .anyMatch(field -> field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(IdSection.class));
    }
}
